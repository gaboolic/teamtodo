package tk.gbl.agent.request;

/**
 * Date: 2015/4/23
 * Time: 9:12
 *
 * @author Tian.Dong
 */
public class BaseAccountRequest extends BaseRequest {
  private String username;

  private String password;

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
}
