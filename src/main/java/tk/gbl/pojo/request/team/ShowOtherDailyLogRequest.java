package tk.gbl.pojo.request.team;

import tk.gbl.pojo.request.BaseRequest;

/**
 * Date: 2015/4/15
 * Time: 11:39
 *
 * @author Tian.Dong
 */
public class ShowOtherDailyLogRequest extends BaseRequest {
  Integer userId;

  /**
   * yyyy-MM
   * 如2015-04
   */
  String yearMonth;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getYearMonth() {
    return yearMonth;
  }

  public void setYearMonth(String yearMonth) {
    this.yearMonth = yearMonth;
  }
}
