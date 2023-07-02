package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.AdminLoginPojo;

import java.util.List;

/**
 * Date: 2015/5/21
 * Time: 17:58
 *
 * @author Tian.Dong
 */
public class AdminLoginListResponse extends BaseResponse {
  List<AdminLoginPojo> loginList;

  public AdminLoginListResponse(ResultType type) {
    super(type);
  }

  public List<AdminLoginPojo> getLoginList() {
    return loginList;
  }

  public void setLoginList(List<AdminLoginPojo> loginList) {
    this.loginList = loginList;
  }
}
