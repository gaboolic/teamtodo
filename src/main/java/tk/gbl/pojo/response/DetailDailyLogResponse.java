package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.DailyLogPojo;
import tk.gbl.pojo.DailyLogVisitPojo;

import java.util.List;

/**
 * Date: 2015/4/9
 * Time: 18:18
 *
 * @author Tian.Dong
 */
public class DetailDailyLogResponse extends BaseResponse {
  DailyLogPojo dailyLog;

  Integer isHave;

  List<DailyLogVisitPojo> visitList;

  public DetailDailyLogResponse(ResultType type) {
    super(type);
  }

  public DailyLogPojo getDailyLog() {
    return dailyLog;
  }

  public void setDailyLog(DailyLogPojo dailyLog) {
    this.dailyLog = dailyLog;
  }

  public Integer getIsHave() {
    return isHave;
  }

  public void setIsHave(Integer isHave) {
    this.isHave = isHave;
  }

  public List<DailyLogVisitPojo> getVisitList() {
    return visitList;
  }

  public void setVisitList(List<DailyLogVisitPojo> visitList) {
    this.visitList = visitList;
  }
}
