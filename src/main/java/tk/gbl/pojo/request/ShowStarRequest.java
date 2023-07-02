package tk.gbl.pojo.request;

/**
 * Date: 2015/4/8
 * Time: 15:46
 *
 * @author Tian.Dong
 */
public class ShowStarRequest extends BaseRequest {
  String yearMonth;

  public String getYearMonth() {
    return yearMonth;
  }

  public void setYearMonth(String yearMonth) {
    this.yearMonth = yearMonth;
  }
}
