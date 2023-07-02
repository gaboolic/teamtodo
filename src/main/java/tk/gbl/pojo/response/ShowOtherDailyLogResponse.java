package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.DailyLogPojo;

import java.util.List;
import java.util.Map;

/**
 * Date: 2015/4/16
 * Time: 11:08
 *
 * @author Tian.Dong
 */
public class ShowOtherDailyLogResponse extends BaseResponse {

  public ShowOtherDailyLogResponse(ResultType type) {
    super(type);
  }

  Map<String,DailyLogPojo> dailyLogMap;

  List<DailyLogPojo> dailyLogList;

  public Map<String, DailyLogPojo> getDailyLogMap() {
    return dailyLogMap;
  }

  public void setDailyLogMap(Map<String, DailyLogPojo> dailyLogMap) {
    this.dailyLogMap = dailyLogMap;
  }

  public List<DailyLogPojo> getDailyLogList() {
    return dailyLogList;
  }

  public void setDailyLogList(List<DailyLogPojo> dailyLogList) {
    this.dailyLogList = dailyLogList;
  }
}
