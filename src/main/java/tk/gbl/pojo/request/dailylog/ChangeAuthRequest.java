package tk.gbl.pojo.request.dailylog;

import tk.gbl.pojo.request.BaseRequest;

/**
 * Date: 2015/4/10
 * Time: 16:10
 *
 * @author Tian.Dong
 */
public class ChangeAuthRequest extends BaseRequest {
  /**
   * -1
   * 0
   *
   * 5,6
   */
  private String auth;

  public String getAuth() {
    return auth;
  }

  public void setAuth(String auth) {
    this.auth = auth;
  }
}
