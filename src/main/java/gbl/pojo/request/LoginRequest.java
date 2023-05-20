package gbl.pojo.request;

import javax.validation.constraints.NotNull;

/**
 * Date: 2014/8/11 Time: 9:57
 *
 * @author Tian.Dong
 */
public class LoginRequest extends BaseRequest {
  /**
   * 用户名
   */
  @NotNull
  private String username;

  /**
   * 密码
   */
  @NotNull
  private String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
