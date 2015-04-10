package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.Resp;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.DailyLogDao;
import tk.gbl.dao.DailyLogVisitDao;
import tk.gbl.dao.UserDao;
import tk.gbl.entity.User;
import tk.gbl.entity.log.DailyLog;
import tk.gbl.entity.log.DailyLogVisit;
import tk.gbl.pojo.DailyLogPojo;
import tk.gbl.pojo.DailyLogVisitPojo;
import tk.gbl.pojo.request.dailylog.*;
import tk.gbl.pojo.request.task.ShowStarRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.pojo.response.DetailDailyLogResponse;
import tk.gbl.pojo.response.ShowStarResponse;
import tk.gbl.util.TransUtil;
import tk.gbl.util.UserInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

  @Resource
  DailyLogVisitDao dailyLogVisitDao;

  @Resource
  UserDao userDao;

  public BaseResponse addDailyLog(AddDailyLogRequest request) {
    DailyLog dailyLog = new DailyLog();
    dailyLog.setDate(request.getDate());
    dailyLog.setTitle(request.getTitle());
    dailyLog.setContent(request.getContent());
    dailyLog.setUser(UserInfo.getUser());
    dailyLogDao.save(dailyLog);
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    return response;
  }

  public BaseResponse updateDailyLog(UpdateDailyLogRequest request) {
    User user = UserInfo.getUser();
    DailyLog dailyLog = dailyLogDao.get(request.getId());
    if (dailyLog == null) {
      return Resp.success;
    }
    if (!dailyLog.getUser().getId().equals(user.getId())) {
      return Resp.noAuth;
    }
    dailyLog.setTitle(request.getTitle());
    dailyLog.setContent(request.getContent());
    dailyLogDao.update(dailyLog);
    return Resp.success;
  }

  public BaseResponse deleteDailyLog(DeleteDailyLogRequest request) {
    User user = UserInfo.getUser();
    DailyLog dailyLog = dailyLogDao.get(request.getId());
    if (dailyLog == null) {
      return Resp.success;
    }
    if (!dailyLog.getUser().getId().equals(user.getId())) {
      return Resp.noAuth;
    }
    dailyLogDao.delete(dailyLog);
    return Resp.success;
  }

  public BaseResponse showStar(ShowStarRequest request) {
    User user = UserInfo.getUser();
    List<String> dateList = dailyLogDao.findDistinctDateOfUser(request, user);
    ShowStarResponse response = new ShowStarResponse(ResultType.SUCCESS);
    response.setDateList(dateList);
    return response;
  }

  public BaseResponse detailDailyLog(DetailDailyLogRequest request) {
    DailyLog dailyLog = dailyLogDao.getDetail(request.getDate());
    DetailDailyLogResponse response = new DetailDailyLogResponse(ResultType.SUCCESS);
    if (dailyLog == null) {
      response.setIsHave(0);
    } else {
      DailyLogVisit dailyLogVisit = dailyLogVisitDao.getByDailyLogAndUser(dailyLog,UserInfo.getUser());
      if(dailyLogVisit == null) {
        dailyLogVisit = new DailyLogVisit();
      }
      User user = UserInfo.getUser();
      dailyLogVisit.setUser(user);
      dailyLogVisit.setDailyLog(dailyLog);
      dailyLogVisit.setCreateTime(new Date());
      dailyLogVisit.setName(user.getName());
      dailyLogVisit.setHeadImage(user.getHeadImage());
      dailyLogVisitDao.saveOrUpdate(dailyLogVisit);

      List<DailyLogVisitPojo> visits = new ArrayList<DailyLogVisitPojo>();
      for(DailyLogVisit dbDailyLogVisit:dailyLog.getVisits()) {
        DailyLogVisitPojo dailyLogVisitPojo = new DailyLogVisitPojo();
        dailyLogVisitPojo.setHeadImage(dbDailyLogVisit.getUser().getHeadImage());
        dailyLogVisitPojo.setCreateTime(dbDailyLogVisit.getCreateTime());
        dailyLogVisitPojo.setName(dbDailyLogVisit.getUser().getName());
        visits.add(dailyLogVisitPojo);
      }

      response.setIsHave(1);
      response.setDailyLog(TransUtil.gen(dailyLog, DailyLogPojo.class));
      response.setVisitList(visits);
    }
    return response;
  }

  public BaseResponse changeAuth(ChangeAuthRequest request) {
    User user = UserInfo.getUser();
    user.setAuth(request.getAuth());
    userDao.updateAuth(user);
    return Resp.success;
  }
}
