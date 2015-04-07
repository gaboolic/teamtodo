package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.dao.DailyLogDao;
import tk.gbl.pojo.request.dailylog.AddDailyLogRequest;
import tk.gbl.pojo.request.dailylog.UpdateDailyLogRequest;
import tk.gbl.pojo.response.BaseResponse;

/**
 * Date: 2015/4/6
 * Time: 10:17
 *
 * @author Tian.Dong
 */
@Service
public class DailyLogService {
  DailyLogDao dailyLogDao;

  public BaseResponse addDailyLog(AddDailyLogRequest request) {
    return null;
  }

  public BaseResponse updateDailyLog(UpdateDailyLogRequest request) {
    return null;
  }
}
