package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.UserPojo;

import java.util.List;

/**
 * Date: 2015/5/21
 * Time: 18:00
 *
 * @author Tian.Dong
 */
public class AdminGetAuthListResponse extends BaseResponse {
  List<UserPojo> authList;

  public AdminGetAuthListResponse(ResultType type) {
    super(type);
  }

  public List<UserPojo> getAuthList() {
    return authList;
  }

  public void setAuthList(List<UserPojo> authList) {
    this.authList = authList;
  }
}
