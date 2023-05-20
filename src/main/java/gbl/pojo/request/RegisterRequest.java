package gbl.pojo.request;

import tk.gbl.anno.ValidField;

/**
 * Date: 2014/8/10
 * Time: 20:33
 *
 * @author Tian.Dong
 */
public class RegisterRequest extends BaseRequest {
  /**
   * 用户名
   */
  @ValidField(regex="[\\w]+")
  private String username;

  /**
   * 密码
   */
  @ValidField(regex="[\\w]+")
  private String password;


  /**
   * 手机验证码
   */
  private String checkCode;

  private String email;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCheckCode() {
    return checkCode;
  }

  public void setCheckCode(String checkCode) {
    this.checkCode = checkCode;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
