package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.agent.request.TokenLoginRequest;
import tk.gbl.agent.response.TokenLoginResponse;
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
public class UserService {

  @Resource
  OrgService orgService;

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
    if(sign == null || sign.equals("")) {
      sign = request.getToken();
    }
    //如果本地数据库中sign存在 说明刚刚登录同步过
    User user = null;
    user = userCURDService.findByToken(sign);
    if (user != null) {
      orgService.syncOrg(teamCURDService,userCURDService,user,request);
      return doSignByUser(user, session);
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
      orgService.syncOrg(teamCURDService,userCURDService,user,request);
      return doSignByUser(user, session);
    }
    //token 和 accNbr都没有 说明是新用户
    user = new User();
    user.setSsoId(accNbr);
    user.setName(simUser.getUser_name());
    user.setNickname(simUser.getNick_name());
    user.setHeadImage(simUser.getPic_url());
    user.setSexType(simUser.getSex());
    user.setSex(simUser.getSex().equals("1") ? "男" : "女");
    user.setType(simUser.getManager());
    if (simUser.getManager().equals("1")) {
      user.setTypeName("部门管理员");
    } else if (simUser.getManager().equals("2")) {
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

    orgService.syncOrg(teamCURDService,userCURDService,user,request);
    return doSignByUser(user, session);
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

}
