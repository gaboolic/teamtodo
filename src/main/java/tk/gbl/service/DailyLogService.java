package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.Resp;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.*;
import tk.gbl.entity.User;
import tk.gbl.entity.log.*;
import tk.gbl.pojo.*;
import tk.gbl.pojo.request.ShowStarRequest;
import tk.gbl.pojo.request.dailylog.*;
import tk.gbl.pojo.response.*;
import tk.gbl.util.DateUtil;
import tk.gbl.util.TransUtil;
import tk.gbl.util.UserInfo;

import javax.annotation.Resource;
import java.util.*;

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
  DailyLogReplyDao dailyLogReplyDao;

  @Resource
  DailyLogAtDao dailyLogAtDao;

  @Resource
  DailyLogFileDao dailyLogFileDao;

  @Resource
  UserDao userDao;

  @Resource
  MessageService messageService;

  public BaseResponse addDailyLog(AddDailyLogRequest request) {
    DailyLog diary = dailyLogDao.getDetail(request.getDate(), UserInfo.getUser());
    if (diary != null) {
      return Resp.exist;
    }
    DailyLog dailyLog = new DailyLog();
    dailyLog.setDate(request.getDate());
    dailyLog.setTitle(request.getTitle());
    dailyLog.setContent(request.getContent());
    dailyLog.setUser(UserInfo.getUser());
    dailyLog.setUsername(UserInfo.getUser().getName());
    dailyLog.setHeadImage(UserInfo.getUser().getHeadImage());
    if (UserInfo.getUser().getType() != null && UserInfo.getUser().getType().equals("2")) {
      dailyLog.setStar("1");
    }
    dailyLog.setTeam(UserInfo.getUser().getTeam());
    dailyLogDao.save(dailyLog);
    if (request.getAt() != null && request.getAt().length() > 0) {
      String[] idStrList = request.getAt().split(",");
      Set<Integer> idSet = new HashSet<Integer>();
      for (String idStr : idStrList) {
        if (idStr == null || idStr.length() == 0) {
          continue;
        }
        int id;
        try {
          id = Integer.parseInt(idStr);
        } catch (Exception e) {
          continue;
        }
        idSet.add(id);
      }
      String atNames = "";
      //to do 艾特消息
      for (int id : idSet) {
        User atUser = userDao.get(id);
        if (atUser == null) {
          continue;
        }
        DailyLogAt dailyLogAt = new DailyLogAt();
        dailyLogAt.setDailyLog(dailyLog);
        dailyLogAt.setName(atUser.getName());
        atNames += atUser.getName() + " ";
        dailyLogAt.setCreateTime(new Date());
        dailyLogAt.setHeadImage(atUser.getHeadImage());
        dailyLogAt.setUser(atUser);
        dailyLogAtDao.save(dailyLogAt);
        messageService.atMessage(UserInfo.getUser(), atUser,dailyLog);
      }
      dailyLog.setAtNames(atNames);
    }
    if (request.getUploads() != null && request.getUploads().length() > 0) {
      String[] items = request.getUploads().split(",,,");
      dailyLog.setFileCount(items.length);
      if (items.length > 0) {
        for (String item : items) {
          String[] ite = item.split(",,");
          DailyLogFile dailyLogFile = new DailyLogFile();
          dailyLogFile.setDailyLog(dailyLog);
          dailyLogFile.setCreateTime(new Date());
          dailyLogFile.setPath(ite[0]);
          dailyLogFile.setName(ite[1]);
          dailyLogFileDao.save(dailyLogFile);
        }
      }
    }
    BaseIdResponse response = new BaseIdResponse(ResultType.SUCCESS);
    response.setId(dailyLog.getId());
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
    DailyLog dailyLog = null;
    if (request.getId() != null) {
      dailyLog = dailyLogDao.getDetail(request.getId());
    } else {
      dailyLog = dailyLogDao.getDetail(request.getDate(), UserInfo.getUser());
    }
    DetailDailyLogResponse response = new DetailDailyLogResponse(ResultType.SUCCESS);
    if (dailyLog == null) {
      response.setIsHave(0);
    } else {
      DailyLogVisit dailyLogVisit = dailyLogVisitDao.getByDailyLogAndUser(dailyLog, UserInfo.getUser());
      if (dailyLogVisit == null) {
        dailyLogVisit = new DailyLogVisit();
      }


      User user = UserInfo.getUser();
      if (!user.getId().equals(dailyLog.getUser().getId())) {
        dailyLogVisit.setUser(user);
        dailyLogVisit.setDailyLog(dailyLog);
        dailyLogVisit.setCreateTime(new Date());
        dailyLogVisit.setName(user.getName());
        dailyLogVisit.setHeadImage(user.getHeadImage());
        dailyLogVisitDao.saveOrUpdate(dailyLogVisit);

        dailyLog.setViewCount(dailyLog.getViewCount() + 1);
        dailyLogDao.update(dailyLog);
      }
      List<DailyLogVisitPojo> visits = new ArrayList<DailyLogVisitPojo>();
      for (DailyLogVisit dbDailyLogVisit : dailyLog.getVisits()) {
        DailyLogVisitPojo dailyLogVisitPojo = new DailyLogVisitPojo();
        dailyLogVisitPojo.setHeadImage(dbDailyLogVisit.getUser().getHeadImage());
        dailyLogVisitPojo.setCreateTime(dbDailyLogVisit.getCreateTime());
        dailyLogVisitPojo.setName(dbDailyLogVisit.getUser().getName());
        visits.add(dailyLogVisitPojo);
      }
      List<DailyLogReplyPojo> replies = new ArrayList<DailyLogReplyPojo>();
      for (DailyLogReply dbReply : dailyLog.getReplies()) {
        DailyLogReplyPojo dailyLogReplyPojo = TransUtil.gen(dbReply, DailyLogReplyPojo.class);
        replies.add(dailyLogReplyPojo);
      }
      List<DailyLogAtPojo> ats = new ArrayList<DailyLogAtPojo>();
      for (DailyLogAt dbAt : dailyLog.getAts()) {
        DailyLogAtPojo dailyLogReplyPojo = TransUtil.gen(dbAt, DailyLogAtPojo.class);
        ats.add(dailyLogReplyPojo);
      }
      List<DailyLogFilePojo> files = new ArrayList<DailyLogFilePojo>();
      for (DailyLogFile dbAt : dailyLog.getFiles()) {
        DailyLogFilePojo dailyLogReplyPojo = TransUtil.gen(dbAt, DailyLogFilePojo.class);
        files.add(dailyLogReplyPojo);
      }

      response.setIsHave(1);
      DailyLogPojo dailyLogPojo = TransUtil.gen(dailyLog, DailyLogPojo.class);
      dailyLogPojo.setReplaceContent(dailyLogPojo.getContent().replaceAll("<.*?>",""));
      response.setDailyLog(dailyLogPojo);
      response.setVisitList(visits);
      response.setReplyList(replies);
      response.setAtList(ats);
      response.setFileList(files);
    }
    return response;
  }

  public BaseResponse changeAuth(ChangeAuthRequest request) {
    User user = UserInfo.getUser();
    user.setAuth(request.getAuth());
    if(!request.getAuth().equals("-1") && !request.getAuth().equals("0")) {
      if (request.getAuth().endsWith(",")) {
        request.setAuth(request.getAuth().substring(0, request.getAuth().length() - 1));
      }
      if (!request.getAuth().equals("")) {
        Set<User> userAuths = new HashSet<User>();
        for (String idStr : request.getAuth().split(",")) {
          User authUser = userDao.get(Integer.valueOf(idStr));
//          UserAuth userAuth = new UserAuth();
//          userAuth.setUser(user);
//          userAuth.setAuthUser(authUser);
          userAuths.add(authUser);
        }
        user.setUserAuthList(userAuths);
      }
    }
    userDao.update(user);
    return Resp.success;
  }

  public BaseResponse replyDailyLog(ReplyDailyLogRequest request) {
    User user = UserInfo.getUser();
    DailyLogReply dailyLogReply = new DailyLogReply();
    dailyLogReply.setContent(request.getContent());
    DailyLog dailyLog = new DailyLog();
    dailyLog.setId(request.getId());
    DailyLogReply reply = new DailyLogReply();
    reply.setDailyLog(dailyLog);
    reply.setContent(request.getContent());
    reply.setCreateTime(new Date());
    reply.setUser(user);
    reply.setName(user.getName());
    reply.setHeadImage(user.getHeadImage());
    dailyLogReplyDao.save(reply);

    DailyLog hostDailyLog = dailyLogDao.getDetail(request.getId());
    User toUser = hostDailyLog.getUser();
    if (!user.getId().equals(toUser.getId())) {
      messageService.replyDiaryMessage(user, toUser,hostDailyLog);
    }
    ReplyResponse response = new ReplyResponse(ResultType.SUCCESS);
    response.setName(user.getName());
    response.setHeadImage(user.getHeadImage());
    response.setId(reply.getId());
    return response;
  }

  public BaseResponse listDailyLog(ListDailyLogRequest request) {
    List<DailyLog> dailyLogs = dailyLogDao.list(UserInfo.getUser(), request.getPage(), 20);
    List<DailyLogPojo> dailyLogPojoList = new ArrayList<DailyLogPojo>();
    for (DailyLog dbDailyLog : dailyLogs) {
      DailyLogPojo dailyLogPojo = TransUtil.gen(dbDailyLog, DailyLogPojo.class);
      dailyLogPojo.setMonth(DateUtil.getCnMonth(dailyLogPojo.getDate()));
      dailyLogPojo.setDay(DateUtil.getDay(dailyLogPojo.getDate()));
      dailyLogPojoList.add(dailyLogPojo);
    }
    ListDailyLogResponse response = new ListDailyLogResponse(ResultType.SUCCESS);
    response.setDailyLogList(dailyLogPojoList);
    response.setPage(request.getPage());
    return response;
  }

  public BaseResponse findAuth() {
    User user = UserInfo.getUser();
    user = userDao.getDetail(user.getId());
    ShowAuthResponse response = new ShowAuthResponse(ResultType.SUCCESS);
    response.setAuth(user.getAuth());
    List<UserPojo> userList = new ArrayList<UserPojo>();
    for(User dbUser:user.getUserAuthList()) {
      UserPojo userPojo = TransUtil.gen(dbUser,UserPojo.class);
      userList.add(userPojo);
    }
    response.setAuthUsers(userList);
    return response;
  }

  public BaseResponse canCreate() {
    User user = UserInfo.getUser();
    String today = DateUtil.getDateStr(new Date());
    DailyLog dailyLog = dailyLogDao.getDetail(today,user);

    CanCreateResponse response = new CanCreateResponse(ResultType.SUCCESS);
    if(dailyLog == null) {
      response.setIsCanCreate(1);
    } else {
      response.setIsCanCreate(0);
    }
    return response;
  }
}
