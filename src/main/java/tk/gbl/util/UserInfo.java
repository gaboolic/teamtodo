package tk.gbl.util;

import tk.gbl.entity.User;

import javax.servlet.http.HttpSession;

/**
 * 代码里不用显式的给HttpSession赋值
 * 易于测试
 *
 * Date: 2015/4/10
 * Time: 15:16
 *
 * @author Tian.Dong
 */
public class UserInfo {
  public static void setSession(HttpSession session) {
    UserTokenWrapper.getWrapper().setHttpSession(session);
  }

  public static HttpSession getSession() {
    return UserTokenWrapper.getWrapper().getHttpSession();
  }

  public static void setIp(String ip) {
    UserTokenWrapper.getWrapper().setIp(ip);
  }

  public static String getIp() {
    return UserTokenWrapper.getWrapper().getIp();
  }

  public static User getUser() {
    HttpSession session = UserTokenWrapper.getWrapper().getHttpSession();
    if (session != null) {
      return (User) session.getAttribute("user");
    }
    return null;
  }

  public static User getAdmin() {
    HttpSession session = UserTokenWrapper.getWrapper().getHttpSession();
    if (session != null) {
      return (User) session.getAttribute("admin");
    }
    return null;
  }
}
