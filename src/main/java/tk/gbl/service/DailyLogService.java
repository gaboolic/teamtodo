package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.DailyLogDao;
import tk.gbl.entity.User;
import tk.gbl.entity.log.DailyLog;
import tk.gbl.pojo.DailyLogPojo;
import tk.gbl.pojo.request.dailylog.AddDailyLogRequest;
import tk.gbl.pojo.request.dailylog.DeleteDailyLogRequest;
import tk.gbl.pojo.request.dailylog.DetailDailyLogRequest;
import tk.gbl.pojo.request.dailylog.UpdateDailyLogRequest;
import tk.gbl.pojo.request.task.ShowStarRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.pojo.response.DetailDailyLogResponse;
import tk.gbl.pojo.response.ShowStarResponse;
import tk.gbl.util.TransUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Date: 2015/4/6
 * Time: 10:17
 *
 * @author Tian.Dong
 */
@Service
public class DailyLogService {
  @Resource
  DailyLogDao dailyLogDao;

  public BaseResponse addDailyLog(AddDailyLogRequest request) {
    return null;
  }

  public BaseResponse updateDailyLog(UpdateDailyLogRequest request) {
    return null;
  }

  public BaseResponse deleteDailyLog(DeleteDailyLogRequest request) {
    return null;
  }

  public BaseResponse showStar(ShowStarRequest request, HttpSession session) {
    User user = (User) session.getAttribute("user");
    List<String> dateList = dailyLogDao.findDistinctDateOfUser(request, user);
    ShowStarResponse response = new ShowStarResponse(ResultType.SUCCESS);
    response.setDateList(dateList);
    return response;
  }

  public BaseResponse detailDailyLog(DetailDailyLogRequest request) {
    DailyLog dailyLog = dailyLogDao.getDetail(request.getDate());
    DetailDailyLogResponse response = new DetailDailyLogResponse(ResultType.SUCCESS);
    if(dailyLog == null) {
      response.setIsHave(0);
    } else {
      response.setIsHave(1);
      response.setDailyLog(TransUtil.gen(dailyLog,DailyLogPojo.class));
    }
    return response;
  }
}
