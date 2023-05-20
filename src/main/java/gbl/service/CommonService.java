package gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.MessageDao;
import tk.gbl.dao.UserDao;
import tk.gbl.entity.Message;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.AddMessageRequest;
import tk.gbl.pojo.request.DeleteMessageRequest;
import tk.gbl.pojo.request.LoginRequest;
import tk.gbl.pojo.request.RegisterRequest;
import tk.gbl.pojo.response.AddMessageResponse;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.util.MD5Util;
import tk.gbl.util.Token;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * Date: 2014/12/16
 * Time: 11:09
 *
 * @author Tian.Dong
 */
@Service
public class CommonService {

  @Resource
  UserDao userDao;

  @Resource
  MessageDao messageDao;

  public BaseResponse login(LoginRequest request, HttpSession session, HttpServletResponse response) {
    if (!userDao.isConfirm(request)) {
      BaseResponse baseResponse = new BaseResponse(ResultType.LOGIN_FAIL);
      baseResponse.setMessage("用户名或密码不正确");
      return baseResponse;
    }
    User user = userDao.getUser(request);
    session.setAttribute("user", user);
    String up = user.getUid() + "|" + MD5Util.md5(user.getUid() + "gaboolic");
    Cookie cookie = new Cookie("up", up);
    cookie.setMaxAge(7 * 24 * 60 * 60);
    response.addCookie(cookie);
    return new BaseResponse(ResultType.SUCCESS);
  }

  public BaseResponse register(RegisterRequest request, HttpSession session) {
    if (session.getAttribute("isReg") != null) {
      return new BaseResponse(ResultType.SUCCESS);
    }
    if (userDao.isRepeat(request)) {
      return new BaseResponse(ResultType.REGISTER_REPEAT);
    }
    User user = new User();
    user.setUname(request.getUsername());
    user.setPassword(request.getPassword());
    user.setEmail(request.getEmail());
    userDao.save(user);
    user = userDao.getUser(request.getUsername());
    session.setAttribute("user", user);
    session.setAttribute("isReg", "true");
    return new BaseResponse(ResultType.SUCCESS);
  }

  public AddMessageResponse addMessage(AddMessageRequest request, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return new AddMessageResponse(ResultType.NOT_LOGIN);
    }
    Message message = new Message();
    message.setContent(request.getContent());
    message.setUid(user.getUid());
    message.setUname(user.getUname());
    message.setEmail(user.getEmail());
    message.setMsgTime(new Timestamp(Token.getToken()));
    messageDao.save(message);
    AddMessageResponse response = new AddMessageResponse(ResultType.SUCCESS);
    String content = "%s楼\n" +
        "          %s:\n" +
        "          %s\n" +
        "          <p>%s";
    content = String.format(content, message.getMid(), message.getUname(), message.getContent().replace("<", "&lt").replace(">", "&gt"), message.getMsgTime());

    response.setContent(content);
    return response;
  }

  public BaseResponse deleteMessage(DeleteMessageRequest request, HttpSession session) {
    boolean flag = messageDao.delete(request.getMid());
    if (flag) {
      return new BaseResponse(ResultType.SUCCESS);
    }
    BaseResponse baseResponse = new BaseResponse(ResultType.ERROR);
    baseResponse.setMessage("删除失败");
    return baseResponse;
  }
}
