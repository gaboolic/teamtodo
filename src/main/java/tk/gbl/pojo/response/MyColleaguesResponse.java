package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.UserPojo;

import java.util.List;

/**
 * Date: 2015/4/13
 * Time: 14:28
 *
 * @author Tian.Dong
 */
public class MyColleaguesResponse extends BaseResponse{
  List<UserPojo> myColleagues;

  public MyColleaguesResponse(ResultType type) {
    super(type);
  }

  public List<UserPojo> getMyColleagues() {
    return myColleagues;
  }

  public void setMyColleagues(List<UserPojo> myColleagues) {
    this.myColleagues = myColleagues;
  }
}
