package tk.gbl.pojo.request.dailylog;

import tk.gbl.pojo.request.BaseRequest;

/**
 * Date: 2015/4/21
 * Time: 20:11
 *
 * @author Tian.Dong
 */
public class ListDailyLogRequest extends BaseRequest {
  private Integer page = 1;

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }
}
