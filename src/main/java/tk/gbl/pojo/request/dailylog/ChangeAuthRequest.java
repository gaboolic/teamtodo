package tk.gbl.pojo.request.dailylog;

import tk.gbl.pojo.request.BaseIdRequest;

/**
 * Date: 2015/4/10
 * Time: 16:10
 *
 * @author Tian.Dong
 */
public class ChangeAuthRequest extends BaseIdRequest {
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
