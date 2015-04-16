package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.DailyLogDao;
import tk.gbl.dao.TeamDao;
import tk.gbl.dao.UserDao;
import tk.gbl.entity.User;
import tk.gbl.entity.log.DailyLog;
import tk.gbl.pojo.DailyLogPojo;
import tk.gbl.pojo.UserPojo;
import tk.gbl.pojo.request.team.ShowOtherDailyLogRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.pojo.response.MyColleaguesResponse;
import tk.gbl.pojo.response.ShowHotDiscusResponse;
import tk.gbl.pojo.response.ShowOtherDailyLogResponse;
import tk.gbl.util.TransUtil;
import tk.gbl.util.UserInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2015/4/13
 * Time: 14:22
 *
 * @author Tian.Dong
 */
@Service
public class TeamService {

  @Resource
  TeamDao teamDao;

  @Resource
  UserDao userDao;

  @Resource
  DailyLogDao dailyLogDao;

  public BaseResponse myColleagues() {
    User user = UserInfo.getUser();
    List<User> teamUsers = userDao.getAllOfTeam(user.getTeam());

    List<UserPojo> myColleagues = new ArrayList<UserPojo>();
    for (User colleague : teamUsers) {
      if (colleague.getId().equals(user.getId())) {
        continue;
      }
      myColleagues.add(TransUtil.gen(colleague, UserPojo.class));
    }
    MyColleaguesResponse response = new MyColleaguesResponse(ResultType.SUCCESS);
    response.setMyColleagues(myColleagues);
    return response;
  }

  public BaseResponse showHotDiscus() {
    List<DailyLog> dailyLogs = dailyLogDao.showHotDiscusOfTeam(UserInfo.getUser());
    List<DailyLogPojo> dailyLogPojoList = new ArrayList<DailyLogPojo>();
    for(DailyLog dbDailyLog:dailyLogs) {
      dailyLogPojoList.add(TransUtil.gen(dbDailyLog,DailyLogPojo.class));
    }
    ShowHotDiscusResponse response = new ShowHotDiscusResponse(ResultType.SUCCESS);
    response.setDailyLogList(dailyLogPojoList);
    return response;
  }

  /**
   * 一个人 一个月
   *
   * @param request
   * @return
   */
  public BaseResponse showOtherDailyLog(ShowOtherDailyLogRequest request) {
    List<DailyLog> dailyLogs = dailyLogDao.showOtherDailyLog(request);
    Map<String,DailyLogPojo> map = new HashMap<String,DailyLogPojo>();
    for(DailyLog dbDailyLog:dailyLogs) {
      DailyLogPojo dailyLogPojo = TransUtil.gen(dbDailyLog, DailyLogPojo.class);
      map.put(dailyLogPojo.getDate(),dailyLogPojo);
    }
    ShowOtherDailyLogResponse response = new ShowOtherDailyLogResponse(ResultType.SUCCESS);
    response.setDailyLogMap(map);
    return response;
  }

  public BaseResponse showAtMeDailyLog() {
    dailyLogDao.showAtMeDailyLog(UserInfo.getUser());
    return null;
  }
}
