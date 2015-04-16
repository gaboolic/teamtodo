package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.DailyLogPojo;

import java.util.List;

/**
 * Date: 2015/4/16
 * Time: 10:56
 *
 * @author Tian.Dong
 */
public class ShowAtMeDailyLogResponse extends BaseResponse {
  List<DailyLogPojo> dailyLogList;

  public ShowAtMeDailyLogResponse(ResultType type) {
    super(type);
  }

  public List<DailyLogPojo> getDailyLogList() {
    return dailyLogList;
  }

  public void setDailyLogList(List<DailyLogPojo> dailyLogList) {
    this.dailyLogList = dailyLogList;
  }
}
