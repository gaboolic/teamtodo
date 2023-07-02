package tk.gbl.agent.request;

/**
 * Date: 2015/4/23
 * Time: 9:10
 *
 * @author Tian.Dong
 */
public class BaseTokenRequest extends BaseRequest {
  String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
