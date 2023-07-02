package tk.gbl.service;

import org.springframework.stereotype.Service;
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
import tk.gbl.entity.Team;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.SignRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.util.log.LoggerUtil;
import tk.gbl.util.warp.XmlParse;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Date: 2015/4/8
 * Time: 15:48
 *
 * @author Tian.Dong
 */
@Service
public class UserServiceCopy {


  @Resource
  TeamCURDService teamCURDService;

  @Resource
  UserCURDService userCURDService;

  public BaseResponse signTest(SignRequest request, HttpSession session) {
    User user = userCURDService.findUser(request.getId());
    Team team = teamCURDService.findTeam(user.getTeam().getId());
    user.setTeam(team);
    session.setAttribute("user", user);
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    return response;
  }


  public BaseResponse sign(SignRequest request, HttpSession session) {
    String sign = request.getSign();
    //如果本地数据库中sign存在 说明刚刚登录同步过
    User user = null;
    user = userCURDService.findByToken(sign);
    if (user != null) {
      return doSignByUserExistSyncUserAndOrg(request, user, session);
    }
    //如果不存在 则调用webservice
    TokenLoginRequest tokenLoginRequest = new TokenLoginRequest();
    tokenLoginRequest.setToken(request.getSign());

    LoggerUtil.inOutInfo("tokenLoginStart");
    String tokenLoginResult = SimBaServiceClient.tokenLogin(tokenLoginRequest);
    LoggerUtil.inOutInfo("tokenLoginEnd");

    TokenLoginResponse tokenLoginResponse = null;
    try {
      tokenLoginResponse = XmlParse.parse(tokenLoginResult, TokenLoginResponse.class);
    } catch (Exception e) {
      LoggerUtil.error("", e);
      BaseResponse response = new BaseResponse(ResultType.NO_AUTH);
      response.setMessage("token login 解析失败");
      return response;
    }
    if (!tokenLoginResponse.getResult().getCode().equals("0")) {
      BaseResponse response = new BaseResponse(ResultType.NO_AUTH);
      response.setMessage("token login 失败");
      return response;
    }
    SimUser simUser = tokenLoginResponse.getMessage().getUser();
    String accNbr = simUser.getAcc_nbr();
    //尝试用accNbr获取user
    user = userCURDService.findBySsoId(accNbr);
    if (user != null) {
      return doSignByUserExistSyncOrg(request, user, session);
    }
    //token 和 accNbr都没有 说明是新用户
    user = new User();
    user.setSsoId(accNbr);
    user.setName(simUser.getUser_name());
    user.setNickname(simUser.getNick_name());
    user.setHeadImage(simUser.getPic_url());
    user.setSexType(simUser.getSex());
    user.setSex(simUser.getSex().equals("1")?"男":"女");
    user.setType(simUser.getManager());
    if(simUser.getManager().equals("1")) {
      user.setTypeName("部门管理员");
    } else if(simUser.getManager().equals("2")) {
      user.setTypeName("超级管理员");
    } else {
      user.setTypeName("普通用户");
    }
    if (simUser.getManager().equals("2")) {
      if (user.getUsername() == null) {
        user.setUsername("admin");
        user.setPassword("admin");
      }
    }
    user.setToken(simUser.getToken());
    user.setAuth("0");
    user.setUpdateTime(new Date());
    userCURDService.save(user);

    OrgServiceRequest orgServiceRequest = new OrgServiceRequest();
    orgServiceRequest.setToken(request.getSign());
    GetOrgResponse getOrgResponse = null;
    try {
      getOrgResponse = XmlParse.parse(SimBaServiceClient.orgService(orgServiceRequest), GetOrgResponse.class);
    } catch (Exception e) {
      LoggerUtil.error("同步组织架构异常", e);
      BaseResponse response = new BaseResponse(ResultType.OUT);
      response.setMessage("同步组织架构异常");
      return response;
    }
    if (!getOrgResponse.getResult().getCode().equals("0")) {
      BaseResponse response = new BaseResponse(ResultType.OUT);
      response.setMessage("同步组织架构失败");
      return response;
    }
    Company simCompany = getOrgResponse.getMessage().getCompany();
    Team company = null;
    company = teamCURDService.findBySsoId(simCompany.getId());
    if (company == null) {
      company = new Team();
    }
    company.setSsoId(simCompany.getEnter_id());
    company.setVersion(simCompany.getVer());
    company.setName(simCompany.getName());
    company.setType("0");
    teamCURDService.save(company);

    for (Department simDepartment : simCompany.getDepartments()) {
      dealDepartment(company, company, simDepartment);
    }
    for (Member simMember : simCompany.getMembers()) {
      dealMember(company, company, simMember);
    }


    //User user = userDao.get(Integer.valueOf(request.getSign()));
    return doSignByUser(user, session);
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
    if (simMember.getPic_url() != null
        && simMember.getPic_url().length() > 0
        && simMember.getFace_custom() != null
        && simMember.getFace_custom().length() > 0) {
      user.setHeadImage(simMember.getPic_url() + simMember.getFace_custom());
    } else {
      if (simMember.getSex().equals("1")) {
        user.setHeadImage("/img/man.png");
      } else {
        user.setHeadImage("/img/woman.png");
      }
    }
    userCURDService.saveOrUpdate(user);
  }


  //此处应有递归
  private void dealDepartment(Team company, Team father, Department simDepartment) {
    //判断team表里是否有记录
    Team team = teamCURDService.findBySsoId(simDepartment.getId());
    if (team == null) {
      team = new Team();
    }
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

  private BaseResponse doSignByUser(User user, HttpSession session) {
    if (user.getTeam() != null) {
      Team team = teamCURDService.findTeam(user.getTeam().getId());
      user.setTeam(team);
    }
    session.setAttribute("user", user);
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    return response;
  }

  private BaseResponse doSignByUserExistSyncUserAndOrg(final SignRequest request, final User user, HttpSession session) {
    //需要更新自己头像
    //需要异步更新组织架构
    new Thread() {
      public void run() {
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
          if (simUser.getManager().equals("2")) {
            if (user.getUsername() == null) {
              user.setUsername("admin");
              user.setPassword("admin");
            }
          }
          user.setToken(simUser.getToken());
          userCURDService.update(user);
        }

        //调用组织架构版本 和数据库里比较
        //如果不同 则同步
        if (user.getTeam() == null) {
          return;
        }
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
        company.setVersion(simVersion);
        teamCURDService.update(company);

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
        for (Department simDepartment : simCompany.getDepartments()) {
          dealDepartment(company, company, simDepartment);
        }
        for (Member simMember : simCompany.getMembers()) {
          dealMember(company, company, simMember);
        }

      }
    }.start();
    if (user.getTeam() != null) {
      Team team = teamCURDService.findTeam(user.getTeam().getId());
      user.setTeam(team);
    }

    session.setAttribute("user", user);
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    return response;
  }

  private BaseResponse doSignByUserExistSyncOrg(final SignRequest request, final User user, HttpSession session) {
    //需要异步更新组织架构
    new Thread() {
      public void run() {
        //调用组织架构版本 和数据库里比较
        //如果不同 则同步
        if (user.getTeam() == null) {
          return;
        }
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
        company.setVersion(simVersion);
        teamCURDService.update(company);

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
        for (Department simDepartment : simCompany.getDepartments()) {
          dealDepartment(company, company, simDepartment);
        }
        for (Member simMember : simCompany.getMembers()) {
          dealMember(company, company, simMember);
        }

      }
    }.start();
    if (user.getTeam() != null) {
      Team team = teamCURDService.findTeam(user.getTeam().getId());
      user.setTeam(team);
    }

    session.setAttribute("user", user);
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    return response;
  }


}
