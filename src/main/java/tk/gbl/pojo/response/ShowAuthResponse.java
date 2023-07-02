package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.UserPojo;

import java.util.List;

/**
 * Date: 2015/4/25
 * Time: 15:28
 *
 * @author Tian.Dong
 */
public class ShowAuthResponse extends BaseResponse {
  private String auth;

  private List<UserPojo> authUsers;

  public ShowAuthResponse(ResultType type) {
    super(type);
  }

  public String getAuth() {
    return auth;
  }

  public void setAuth(String auth) {
    this.auth = auth;
  }

  public List<UserPojo> getAuthUsers() {
    return authUsers;
  }

  public void setAuthUsers(List<UserPojo> authUsers) {
    this.authUsers = authUsers;
  }
}
