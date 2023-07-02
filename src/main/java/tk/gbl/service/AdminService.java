package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.agent.request.AccountLoginRequest;
import tk.gbl.agent.request.TokenLoginRequest;
import tk.gbl.agent.response.TokenLoginResponse;
import tk.gbl.agent.service.SimBaServiceClient;
import tk.gbl.constants.Resp;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.AdminLoginDao;
import tk.gbl.dao.TeamDao;
import tk.gbl.dao.UserAuthDao;
import tk.gbl.dao.UserDao;
import tk.gbl.entity.Team;
import tk.gbl.entity.User;
import tk.gbl.entity.admin.AdminLogin;
import tk.gbl.pojo.AdminLoginPojo;
import tk.gbl.pojo.TeamPojo;
import tk.gbl.pojo.UserPojo;
import tk.gbl.pojo.request.BaseIdRequest;
import tk.gbl.pojo.request.SignRequest;
import tk.gbl.pojo.request.admin.DeleteAuthRequest;
import tk.gbl.pojo.request.admin.SetDepManagerRequest;
import tk.gbl.pojo.request.dailylog.ChangeAuthRequest;
import tk.gbl.pojo.request.team.GetTeamOrgRequest;
import tk.gbl.pojo.response.*;
import tk.gbl.util.Base64;
import tk.gbl.util.TransUtil;
import tk.gbl.util.UserInfo;
import tk.gbl.util.log.LoggerUtil;
import tk.gbl.util.warp.XmlParse;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Date: 2015/5/18
 * Time: 13:05
 *
 * @author Tian.Dong
 */
@Service
public class AdminService {
  @Resource
  UserDao userDao;

  @Resource
  TeamDao teamDao;

  @Resource
  UserAuthDao userAuthDao;

  @Resource
  AdminLoginDao adminLoginDao;

  String accountLogin = "1";
  String tokenLogin = "2";
  Integer loginSuccess = 1;
  Integer loginFailure = 0;

  private void writeAdminLogin(SignRequest request, String type, Integer isSuccess) {
    AdminLogin adminLogin = new AdminLogin();
    adminLogin.setUsername(request.getUsername());
    adminLogin.setToken(request.getSign());
    adminLogin.setType(type);
    adminLogin.setIsSuccess(isSuccess);
    adminLogin.setAdmin(UserInfo.getAdmin());
    adminLogin.setCreateTime(new Date());
    adminLogin.setIp(UserInfo.getIp());
    adminLoginDao.save(adminLogin);
  }

  public BaseResponse login(SignRequest request) {
    User user = userDao.getByUsernameAndPassword(request.getUsername(), request.getPassword());
    if (user == null) {
      AccountLoginRequest accountLoginRequest = new AccountLoginRequest();
      accountLoginRequest.setUsername(request.getUsername());
      String password = Base64.getBase64(request.getPassword());
      char[] passwordChs = password.toCharArray();
      for (int i = 0; i < password.length() / 2; i++) {
        char t = passwordChs[i];
        passwordChs[i] = passwordChs[password.length() - i - 1];
        passwordChs[password.length() - i - 1] = t;
      }
      password = String.valueOf(passwordChs);
      accountLoginRequest.setPassword(password);
      String result = SimBaServiceClient.accountLogin(accountLoginRequest);

      TokenLoginResponse loginResponse = null;
      try {
        loginResponse = XmlParse.parse(result, TokenLoginResponse.class);
      } catch (Exception e) {
        LoggerUtil.error("admin login xml parse", e);
        writeAdminLogin(request, accountLogin, loginFailure);
        return Resp.noAuth;
      }
      if (!loginResponse.getResult().getCode().equals("0")) {
        writeAdminLogin(request, accountLogin, loginFailure);
        return Resp.noAuth;
      }
      if (!loginResponse.getMessage().getUser().getManager().equals("2")
          && !loginResponse.getMessage().getUser().getManager().equals("1")) {
        writeAdminLogin(request, accountLogin, loginFailure);
        return Resp.noAuth;
      }
      user = userDao.getBySsoId(loginResponse.getMessage().getUser().getAcc_nbr());
      user.setUsername(request.getUsername());
      user.setPassword(request.getPassword());
      userDao.update(user);
      writeAdminLogin(request, accountLogin, loginSuccess);
      UserInfo.getSession().setAttribute("admin", user);
      return Resp.success;
    }
    if (user.getType() == null) {
      writeAdminLogin(request, accountLogin, loginFailure);
      return Resp.noAuth;
    }
    if (!user.getType().equals("1") && !user.getType().equals("2")) {
      writeAdminLogin(request, accountLogin, loginFailure);
      return Resp.noAuth;
    }
    writeAdminLogin(request, accountLogin, loginSuccess);
    UserInfo.getSession().setAttribute("admin", user);
    return Resp.success;
  }

  public BaseResponse tokenLogin(SignRequest request) {
    TokenLoginRequest tokenLoginRequest = new TokenLoginRequest();
    tokenLoginRequest.setToken(request.getSign());
    String result = SimBaServiceClient.tokenLogin(tokenLoginRequest);

    TokenLoginResponse tokenLoginResponse = null;
    try {
      tokenLoginResponse = XmlParse.parse(result, TokenLoginResponse.class);
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
    if (!tokenLoginResponse.getMessage().getUser().getManager().equals("2")
        || !tokenLoginResponse.getMessage().getUser().getManager().equals("1")) {
      writeAdminLogin(request, accountLogin, loginFailure);
      return Resp.noAuth;
    }
    User user = userDao.getBySsoId(tokenLoginResponse.getMessage().getUser().getAcc_nbr());
    if (user == null) {
      writeAdminLogin(request, tokenLogin, loginFailure);
      return Resp.noAuth;
    }
    writeAdminLogin(request, tokenLogin, loginSuccess);
    UserInfo.getSession().setAttribute("admin", user);
    return Resp.success;
  }

  public BaseResponse loginList() {
    List<AdminLogin> adminLogins = adminLoginDao.getLast();
    List<AdminLoginPojo> adminLoginList = new ArrayList<AdminLoginPojo>();
    for (AdminLogin dbLogin : adminLogins) {
      adminLoginList.add(TransUtil.gen(dbLogin, AdminLoginPojo.class));
    }
    AdminLoginListResponse response = new AdminLoginListResponse(ResultType.SUCCESS);
    response.setLoginList(adminLoginList);
    return response;
  }

  public BaseResponse authList(BaseIdRequest request) {
    AdminGetAuthListResponse response = new AdminGetAuthListResponse(ResultType.SUCCESS);
    User user = userDao.getDetail(request.getId());
    String auth = user.getAuth();
    if (auth.equals("-1")) {
      response.setAuthList(null);
    } else if (auth.equals("0")) {
      Team team = new Team();
      team.setId(user.getTeam().getId());
      List<User> users = userDao.getAllOfTeam(team);
      List<UserPojo> authList = new ArrayList<UserPojo>();
      for (User dbUser : users) {
        UserPojo userPojo = TransUtil.gen(dbUser, UserPojo.class);
        authList.add(userPojo);
      }
      response.setAuthList(authList);
    } else {
      Set<User> users = user.getUserAuthList();
      List<UserPojo> authList = new ArrayList<UserPojo>();
      for (User dbUser : users) {
        UserPojo userPojo = TransUtil.gen(dbUser, UserPojo.class);
        authList.add(userPojo);
      }
      response.setAuthList(authList);
    }
    return response;
  }

  public BaseResponse changeAuth(ChangeAuthRequest request) {
    User user = userDao.get(request.getId());
    user.setAuth(request.getAuth());
    userDao.updateAuth(user);
    return Resp.success;
  }


  public BaseResponse self() {
    User u = UserInfo.getAdmin();
    ShowMessageResponse response = new ShowMessageResponse(ResultType.SUCCESS);
    response.setUser(TransUtil.gen(u, UserPojo.class));
    return response;
  }

  public BaseResponse org(GetTeamOrgRequest request) {
    User user = UserInfo.getAdmin();
    user = userDao.get(user.getId());
    Team team = null;
    if (user.getType().equals("2")) {
      team = teamDao.get(user.getTeam().getId());
    } else if (user.getType().equals("1")) {
      team = teamDao.get(user.getDep().getId());
    } else {
      return Resp.noAuth;
    }

    Team dep = new Team();
    if (request.getDepId() == null) {
      dep.setId(team.getId());
    } else {
      dep.setId(request.getDepId());
    }
    List<Team> depList = teamDao.getAllOfDep(dep);
    List<User> userList = userDao.getAllOfDep(dep);

    List<TeamPojo> departments = new ArrayList<TeamPojo>();
    for (Team dbDep : depList) {
      departments.add(TransUtil.gen(dbDep, TeamPojo.class));
    }
    List<UserPojo> members = new ArrayList<UserPojo>();
    for (User colleague : userList) {
      if (!user.getId().equals(colleague.getId())) {
        members.add(TransUtil.gen(colleague, UserPojo.class));
      }
    }
    MyColleaguesResponse response = new MyColleaguesResponse(ResultType.SUCCESS);
    response.setMembers(members);
    response.setDepartments(departments);
    return response;
  }

  public BaseResponse deleteAuth(DeleteAuthRequest request) {
    return null;
  }

  public BaseResponse setDepManager(SetDepManagerRequest request) {
    Team team = teamDao.get(request.getDepId());
    User owner = new User();
    owner.setId(request.getUserId());
    team.setOwner(owner);
    teamDao.update(team);
    return Resp.success;
  }
}
