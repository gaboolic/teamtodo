package tk.gbl.pojo.request.dailylog;

import tk.gbl.pojo.request.BaseRequest;

/**
 * Date: 2015/4/9
 * Time: 17:26
 *
 * @author Tian.Dong
 */
public class DetailDailyLogRequest extends BaseRequest {
  /**
   * 日期
   * 如2015-04-09
   */
  private String date;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
