package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.DailyLogDao;
import tk.gbl.dao.TeamDao;
import tk.gbl.dao.UserDao;
import tk.gbl.entity.Team;
import tk.gbl.entity.User;
import tk.gbl.entity.log.DailyLog;
import tk.gbl.pojo.DailyLogPojo;
import tk.gbl.pojo.TeamPojo;
import tk.gbl.pojo.UserPojo;
import tk.gbl.pojo.request.team.GetTeamOrgRequest;
import tk.gbl.pojo.request.team.ShowOtherDailyLogRequest;
import tk.gbl.pojo.response.*;
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

  /**
   * 包括自己的组织结构
   */
  public BaseResponse org(GetTeamOrgRequest request) {
    User user = UserInfo.getUser();
    Team dep = null;
    if (request.getDepId() == null) {
      dep = teamDao.get(user.getTeam().getId());
    } else {
      dep = teamDao.get(request.getDepId());
    }

    List<Team> depList = teamDao.getAllOfDep(dep);
    List<User> userList = userDao.getAllOfDep(dep);

    List<TeamPojo> departments = new ArrayList<TeamPojo>();
    for (Team dbDep : depList) {
      departments.add(TransUtil.gen(dbDep, TeamPojo.class));
    }
    List<UserPojo> members = new ArrayList<UserPojo>();
    for (User colleague : userList) {
      UserPojo userPojo = TransUtil.gen(colleague, UserPojo.class);
      if (dep.getOwner() != null && dep.getOwner().getId().equals(colleague.getId())) {
        userPojo.setStar(1);
      }
      if (!colleague.getType().equals("0")) {
        userPojo.setStar(1);
      }
      members.add(userPojo);
    }
    MyColleaguesResponse response = new MyColleaguesResponse(ResultType.SUCCESS);
    response.setMembers(members);
    response.setDepartments(departments);
    return response;
  }

  /**
   * 不包括自己的组织结构
   */
  public BaseResponse myColleagues(GetTeamOrgRequest request) {
    User user = UserInfo.getUser();
    if (user.getTeam() == null) {
      user = userDao.get(user.getId());
      if (user.getTeam() != null) {
        Team team = teamDao.get(user.getTeam().getId());
        user.setTeam(team);
      }
      UserInfo.getSession().setAttribute("user", user);
    }

    Team dep = null;
    if (request.getDepId() == null) {
      dep = teamDao.get(user.getTeam().getId());
    } else {
      dep = teamDao.get(request.getDepId());
    }
    List<Team> depList = teamDao.getAllOfDep(dep);
    List<User> userList = userDao.getAllOfDep(dep);

    List<TeamPojo> departments = new ArrayList<TeamPojo>();
    for (Team dbDep : depList) {
      departments.add(TransUtil.gen(dbDep, TeamPojo.class));
    }
    List<UserPojo> members = new ArrayList<UserPojo>();
    for (User colleague : userList) {
      if (!user.getId().equals(colleague.getId())) {
        UserPojo userPojo = TransUtil.gen(colleague, UserPojo.class);
        if (dep.getOwner() != null && dep.getOwner().getId().equals(colleague.getId())) {
          userPojo.setStar(1);
        }
        if (!colleague.getType().equals("0")) {
          userPojo.setStar(1);
        }
        members.add(userPojo);
      }
    }
    MyColleaguesResponse response = new MyColleaguesResponse(ResultType.SUCCESS);
    response.setMembers(members);
    response.setDepartments(departments);
    return response;
  }

  public BaseResponse showHotDiscus() {
    List<DailyLog> dailyLogs = dailyLogDao.showHotDiscusOfTeam(UserInfo.getUser());
    List<DailyLogPojo> dailyLogPojoList = new ArrayList<DailyLogPojo>();
    for (DailyLog dbDailyLog : dailyLogs) {
      dailyLogPojoList.add(TransUtil.gen(dbDailyLog, DailyLogPojo.class));
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
    Map<String, DailyLogPojo> map = new HashMap<String, DailyLogPojo>();
    for (DailyLog dbDailyLog : dailyLogs) {
      DailyLogPojo dailyLogPojo = TransUtil.gen(dbDailyLog, DailyLogPojo.class);
      map.put(dailyLogPojo.getDate(), dailyLogPojo);
    }
    ShowOtherDailyLogResponse response = new ShowOtherDailyLogResponse(ResultType.SUCCESS);
    response.setDailyLogMap(map);
    return response;
  }

  public BaseResponse showOtherDailyLogList(ShowOtherDailyLogRequest request) {
    List<DailyLog> dailyLogs = dailyLogDao.showOtherDailyLog(request);
    List<DailyLogPojo> list = new ArrayList<DailyLogPojo>();
    for (DailyLog dbDailyLog : dailyLogs) {
      DailyLogPojo dailyLogPojo = TransUtil.gen(dbDailyLog, DailyLogPojo.class);
      list.add(dailyLogPojo);
    }
    ShowOtherDailyLogResponse response = new ShowOtherDailyLogResponse(ResultType.SUCCESS);
    response.setDailyLogList(list);
    return response;
  }

  public BaseResponse showAtMeDailyLog() {
    List<DailyLog> dailyLogs = dailyLogDao.showAtMeDailyLog(UserInfo.getUser());
    List<DailyLogPojo> dailyLogPojoList = new ArrayList<DailyLogPojo>();
    for (DailyLog dbDailyLog : dailyLogs) {
      dailyLogPojoList.add(TransUtil.gen(dbDailyLog, DailyLogPojo.class));
    }
    ShowAtMeDailyLogResponse response = new ShowAtMeDailyLogResponse(ResultType.SUCCESS);
    response.setDailyLogList(dailyLogPojoList);
    return response;
  }


  /**
   * 不包括自己的组织结构
   */
  public BaseResponse myAuthColleagues(GetTeamOrgRequest request) {
    User user = UserInfo.getUser();
    if (user.getTeam() == null) {
      user = userDao.get(user.getId());
      if (user.getTeam() != null) {
        Team team = teamDao.get(user.getTeam().getId());
        user.setTeam(team);
      }
      UserInfo.getSession().setAttribute("user", user);
    }
    Team dep = null;
    if (request.getDepId() == null) {
      dep = teamDao.get(user.getTeam().getId());
    } else {
      dep = teamDao.get(request.getDepId());
    }
    List<Team> depList = teamDao.getAllOfDep(dep);
    List<User> userList = userDao.getAllOfDepAndHaveAuth(dep, user);

    List<TeamPojo> departments = new ArrayList<TeamPojo>();
    for (Team dbDep : depList) {
      departments.add(TransUtil.gen(dbDep, TeamPojo.class));
    }
    List<UserPojo> members = new ArrayList<UserPojo>();
    for (User colleague : userList) {
      if (!user.getId().equals(colleague.getId())) {
        UserPojo userPojo = TransUtil.gen(colleague, UserPojo.class);
        if (dep.getOwner() != null && dep.getOwner().getId().equals(colleague.getId())) {
          userPojo.setStar(1);
        }
        if (!colleague.getType().equals("0")) {
          userPojo.setStar(1);
        }
        members.add(userPojo);
      }
    }
    MyColleaguesResponse response = new MyColleaguesResponse(ResultType.SUCCESS);
    response.setMembers(members);
    response.setDepartments(departments);
    return response;
  }
}
