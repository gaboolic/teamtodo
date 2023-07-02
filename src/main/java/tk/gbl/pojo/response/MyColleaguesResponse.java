package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.TeamPojo;
import tk.gbl.pojo.UserPojo;

import java.util.List;

/**
 * Date: 2015/4/13
 * Time: 14:28
 *
 * @author Tian.Dong
 */
public class MyColleaguesResponse extends BaseResponse {
  List<UserPojo> members;
  List<TeamPojo> departments;

  public MyColleaguesResponse(ResultType type) {
    super(type);
  }

  public List<UserPojo> getMembers() {
    return members;
  }

  public void setMembers(List<UserPojo> members) {
    this.members = members;
  }

  public List<TeamPojo> getDepartments() {
    return departments;
  }

  public void setDepartments(List<TeamPojo> departments) {
    this.departments = departments;
  }
}
