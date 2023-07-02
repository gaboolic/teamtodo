package tk.gbl.pojo.request;

/**
 * Date: 2015/4/8
 * Time: 15:56
 *
 * @author Tian.Dong
 */
public class SignRequest {
  private Integer id;
  private String sign;
  private String token;
  private String username;
  private String password;

  private String isApp;

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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

  public String getIsApp() {
    return isApp;
  }

  public void setIsApp(String isApp) {
    this.isApp = isApp;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
