package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.DailyLogPojo;

import java.util.List;

/**
 * Date: 2015/4/25
 * Time: 15:36
 *
 * @author Tian.Dong
 */
public class ListDailyLogResponse extends BaseResponse{
  List<DailyLogPojo> dailyLogList;

  Integer page;

  public ListDailyLogResponse(ResultType type) {
    super(type);
  }

  public List<DailyLogPojo> getDailyLogList() {
    return dailyLogList;
  }

  public void setDailyLogList(List<DailyLogPojo> dailyLogList) {
    this.dailyLogList = dailyLogList;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }
}
