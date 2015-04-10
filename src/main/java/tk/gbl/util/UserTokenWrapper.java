package tk.gbl.util;

import tk.gbl.entity.User;

import javax.servlet.http.HttpSession;

public class UserTokenWrapper {
  private UserTokenWrapper() {
  }

  private static final ThreadLocal<UserTokenWrapper> localContext = new ThreadLocal<UserTokenWrapper>();


  static {
    initWrapper();
  }

  private String userToken;
  private User user;
  private HttpSession httpSession;

  public HttpSession getHttpSession() {
    return httpSession;
  }

  public void setHttpSession(HttpSession httpSession) {
    this.httpSession = httpSession;
  }

  public String getUserToken() {
    return userToken;
  }

  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }

  public static ThreadLocal<UserTokenWrapper> getLocalContext() {
    return localContext;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  private Throwable value;


  public Throwable getValue() {
    return value;
  }

  public Throwable getAndRemove() {
    Throwable res = value;
    value = null;
    return res;
  }

  public void setValue(Throwable value) {
    this.value = value;
  }

  /**
   * 构造方法
   */
  public static void initWrapper() {
    UserTokenWrapper ctx = getWrapper();
    setWrapper(ctx);
  }

  /**
   * 获取包装器
   *
   * @return ErrorsWrapper
   */
  public static UserTokenWrapper getWrapper() {
    UserTokenWrapper ctx = (UserTokenWrapper) localContext.get();
    if (ctx == null) {
      ctx = new UserTokenWrapper();
      localContext.set(ctx);
    }
    return ctx;
  }

  /**
   * 设置包装器
   *
   * @param wrapper
   */
  public static void setWrapper(UserTokenWrapper wrapper) {
    localContext.set(wrapper);
  }
}