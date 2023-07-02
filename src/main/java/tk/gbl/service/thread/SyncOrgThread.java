package tk.gbl.service.thread;

import tk.gbl.agent.request.GetOrgVersionRequest;
import tk.gbl.agent.request.OrgServiceRequest;
import tk.gbl.agent.request.TokenLoginRequest;
import tk.gbl.agent.response.GetOrgResponse;
import tk.gbl.agent.response.GetOrgVersionResponse;
import tk.gbl.agent.response.TokenLoginResponse;
import tk.gbl.agent.response.inner.Company;
import tk.gbl.agent.response.inner.Department;
import tk.gbl.agent.response.inner.Member;
import tk.gbl.agent.response.inner.SimUser;
import tk.gbl.agent.service.SimBaServiceClient;
import tk.gbl.constants.ResultType;
import tk.gbl.constants.SimbaUrl;
import tk.gbl.entity.Team;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.SignRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.service.TeamCURDService;
import tk.gbl.service.UserCURDService;
import tk.gbl.service.UserTeamCURDService;
import tk.gbl.util.log.LoggerUtil;
import tk.gbl.util.warp.XmlParse;

import java.util.Date;

/**
 * Date: 2015/6/10
 * Time: 17:13
 *
 * @author Tian.Dong
 */
public class SyncOrgThread implements Runnable {
  private TeamCURDService teamCURDService;

  private UserTeamCURDService userTeamCURDService;

  private UserCURDService userCURDService;

  private SignRequest request;
  private User user;

  public TeamCURDService getTeamCURDService() {
    return teamCURDService;
  }

  public void setTeamCURDService(TeamCURDService teamCURDService) {
    this.teamCURDService = teamCURDService;
  }

  public SignRequest getRequest() {
    return request;
  }

  public void setRequest(SignRequest request) {
    this.request = request;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public UserCURDService getUserCURDService() {
    return userCURDService;
  }

  public void setUserCURDService(UserCURDService userCURDService) {
    this.userCURDService = userCURDService;
  }

  public UserTeamCURDService getUserTeamCURDService() {
    return userTeamCURDService;
  }

  public void setUserTeamCURDService(UserTeamCURDService userTeamCURDService) {
    this.userTeamCURDService = userTeamCURDService;
  }

  @Override
  public void run() {
    if (user.getTeam() != null) {
      if (user.getUpdateTime() != null && System.currentTimeMillis() - user.getUpdateTime().getTime() < 60 * 5 * 1000) {
        return;
      }
    }

    //更新自己的头像
    TokenLoginRequest tokenLoginRequest = new TokenLoginRequest();

    tokenLoginRequest.setToken(request.getSign());
    String tokenLoginResult = SimBaServiceClient.tokenLogin(tokenLoginRequest);

    TokenLoginResponse tokenLoginResponse = null;
    try {
      tokenLoginResponse = XmlParse.parse(tokenLoginResult, TokenLoginResponse.class);
    } catch (Exception e) {
      LoggerUtil.error("", e);
      return;
    }
    if (tokenLoginResponse.getResult().getCode().equals("0")) {
      SimUser simUser = tokenLoginResponse.getMessage().getUser();
      user.setName(simUser.getUser_name());
      user.setNickname(simUser.getNick_name());
      user.setHeadImage(simUser.getPic_url());
      user.setType(simUser.getManager());
      if (simUser.getManager().equals("2")
          || simUser.getManager().equals("1")) {
        if (user.getUsername() == null) {
          user.setUsername("admin");
          user.setPassword("admin");
        }
      }
      user.setToken(simUser.getToken());
      user.setUpdateTime(new Date());
      userCURDService.update(user);
    }

    //调用组织架构版本 和数据库里比较
    //如果不同 则同步
    if (user.getTeam() != null) {

      Team company = teamCURDService.findTeam(user.getTeam().getId());

      GetOrgVersionRequest getOrgVersionRequest = new GetOrgVersionRequest();
      getOrgVersionRequest.setEnterId(company.getSsoId());
      getOrgVersionRequest.setToken(request.getSign());
      String orgVersionResult = SimBaServiceClient.orgServiceVersion(getOrgVersionRequest);
      GetOrgVersionResponse getOrgVersionResponse = null;
      try {
        getOrgVersionResponse = XmlParse.parse(orgVersionResult, GetOrgVersionResponse.class);
      } catch (Exception e) {
        LoggerUtil.error("获取组织架构version异常", e);
        return;
      }
      if (!getOrgVersionResponse.getResult().getCode().equals("0")) {
        BaseResponse response = new BaseResponse(ResultType.OUT);
        response.setMessage("获取组织架构version失败");
        return;
      }
      String simVersion = getOrgVersionResponse.getMessage().getInfo().getXml_version();
      if (company.getVersion() != null && company.getVersion().equals(simVersion)) {
        return;
      }
      //update company
      company.setUpdateTime(new Date());
      company.setVersion(simVersion);
      teamCURDService.update(company);

      user.setVersion(simVersion);
      user.setUpdateTime(new Date());
      userCURDService.update(user);
    }


    OrgServiceRequest orgServiceRequest = new OrgServiceRequest();
    orgServiceRequest.setToken(request.getSign());
    GetOrgResponse getOrgResponse = null;
    try {
      getOrgResponse = XmlParse.parse(SimBaServiceClient.orgService(orgServiceRequest), GetOrgResponse.class);
    } catch (Exception e) {
      LoggerUtil.error("同步组织架构异常", e);
      return;
    }
    if (!getOrgResponse.getResult().getCode().equals("0")) {
      return;
    }


    Company simCompany = getOrgResponse.getMessage().getCompany();
    Team company = null;
    company = teamCURDService.findBySsoId(simCompany.getEnter_id());
    if (company == null) {
      company = new Team();
    }
    company.setUpdateTime(new Date());
    company.setSsoId(simCompany.getEnter_id());
    company.setVersion(simCompany.getVer());
    company.setName(simCompany.getName());
    company.setType("0");
    teamCURDService.saveOrUpdate(company);

    for (Department simDepartment : simCompany.getDepartments()) {
      dealDepartment(company, company, simDepartment);
    }
    for (Member simMember : simCompany.getMembers()) {
      dealMember(company, company, simMember);
    }

  }

  private void dealMember(Team company, Team dep, Member simMember) {
    //判断user表里是否有记录
    User user = userCURDService.findBySsoId(simMember.getNbr());
    if (user == null) {
      user = new User();
      user.setType("0");
      user.setTypeName("普通用户");
      user.setAuth("0");
    }
    if (user.getUpdateTime() != null && user.getTeam() != null && System.currentTimeMillis() - user.getUpdateTime().getTime() < 60 * 5 * 1000) {
      return;
    }
    user.setVersion(company.getVersion());
    user.setUpdateTime(new Date());
    user.setName(simMember.getName());
    user.setSsoId(simMember.getNbr());
    user.setTeam(company);
    user.setTeamName(company.getName());
    user.setDep(dep);
    user.setDepName(dep.getName());

    user.setSexType(simMember.getSex());
    if (simMember.getSex().equals("1")) {
      user.setSex("男");
    } else {
      user.setSex("女");
    }
    if (simMember.getFace_custom() != null && !simMember.getFace_custom().equals("")) {
      if (simMember.getPic_url() != null && !simMember.getPic_url().equals("")) {
        //两个拼起来
        String url = simMember.getPic_url() + simMember.getFace_custom();
        url = url.substring(0, url.length() - 4);
        url = url + "a.png";
        user.setHeadImage(url);
      } else {
        String url = SimbaUrl.imgPath + simMember.getFace_custom();
        url = url.substring(0, url.length() - 4);
        url = url + "a.png";
        user.setHeadImage(url);
      }
    } else {
      if (simMember.getSex() != null && simMember.getSex().equals("1")) {
        user.setHeadImage("http://218.104.238.212:8080/Simba-WS/images/default/1_a.png");
      } else {
        user.setHeadImage("http://218.104.238.212:8080/Simba-WS/images/default/2_a.png");
      }
    }
//    if (simMember.getPic_url() != null
//        && simMember.getPic_url().length() > 0
//        && simMember.getFace_custom() != null
//        && simMember.getFace_custom().length() > 0) {
//      user.setHeadImage(simMember.getPic_url() + simMember.getFace_custom());
//    } else {
//      if (simMember.getSex().equals("1")) {
//        user.setHeadImage("/img/man.png");
//      } else {
//        user.setHeadImage("/img/woman.png");
//      }
//    }
    userCURDService.saveOrUpdate(user);
  }


  //此处应有递归
  private void dealDepartment(Team company, Team father, Department simDepartment) {
    //判断team表里是否有记录
    Team team = teamCURDService.findBySsoId(simDepartment.getId());
    if (team == null) {
      team = new Team();
    }
    if (team.getUpdateTime() != null && team.getCompany() != null && System.currentTimeMillis() - team.getUpdateTime().getTime() < 60 * 5 * 1000) {
      return;
    }
    team.setUpdateTime(new Date());
    team.setVersion(company.getVersion());
    team.setSsoId(simDepartment.getId());
    team.setName(simDepartment.getName());
    team.setFather(father);
    team.setCompany(company);
    teamCURDService.saveOrUpdate(team);

    for (Department simSumDepartment : simDepartment.getDepartments()) {
      dealDepartment(company, team, simSumDepartment);
    }
    for (Member simSumMember : simDepartment.getMembers()) {
      dealMember(company, team, simSumMember);
    }

  }
}

