package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.Resp;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.TaskDao;
import tk.gbl.dao.TaskReplyDao;
import tk.gbl.dao.UserDao;
import tk.gbl.entity.Task;
import tk.gbl.entity.TaskReply;
import tk.gbl.entity.Team;
import tk.gbl.entity.User;
import tk.gbl.pojo.TaskPojo;
import tk.gbl.pojo.TaskReplyPojo;
import tk.gbl.pojo.UserPojo;
import tk.gbl.pojo.request.ShowStarRequest;
import tk.gbl.pojo.request.task.*;
import tk.gbl.pojo.response.*;
import tk.gbl.util.DateUtil;
import tk.gbl.util.TransUtil;
import tk.gbl.util.UserInfo;

import javax.annotation.Resource;
import java.util.*;

/**
 * Date: 2015/4/1
 * Time: 15:11
 *
 * @author Tian.Dong
 */
@Service
public class TaskService {

  @Resource
  TaskDao taskDao;

  @Resource
  UserDao userDao;

  @Resource
  TaskReplyDao taskReplyDao;

  @Resource
  MessageService messageService;

  public BaseResponse addTask(AddTaskRequest request) {
    Task task = new Task();
    task.setLevel(request.getLevel());
    task.setType(request.getType());
    task.setDate(request.getDate());
    task.setUser(UserInfo.getUser());
    task.setUsername(UserInfo.getUser().getName());
    task.setHeadImage(UserInfo.getUser().getHeadImage());
    task.setCardId(request.getCardId());
    task.setTitle(request.getTitle());
    task.setContent(request.getContent());
    task.setStartTime(DateUtil.getDateStr(new Date()));
    task.setEndTime(DateUtil.getDateStr(new Date()));
    task.setAuth(request.getAuth());
    task.setCreateTime(new Date());

    Set<User> joins = new HashSet<User>();
    if (request.getJoinIds() != null) {
      if (request.getJoinIds().endsWith(",")) {
        request.setJoinIds(request.getJoinIds().substring(0, request.getJoinIds().length() - 1));
      }
      String joinNames = "";
      if (!request.getJoinIds().equals("")) {
        for (String idStr : request.getJoinIds().split(",")) {
          User join = userDao.get(Integer.valueOf(idStr));
          joins.add(join);
          joinNames += join.getName() + "、";
        }
        if (joinNames.length() > 0) {
          joinNames = joinNames.substring(0, joinNames.length() - 1);
        }
      }
      task.setTaskJoins(joins);
      task.setJoinNames(joinNames);
      task.setDownAccept(1);
    }
    taskDao.save(task);

    //参与人消息
    if (joins.size() > 0) {
      for (User join : joins) {
        messageService.downAcceptMessage(UserInfo.getUser(), join, task);
      }
    }

    BaseIdResponse response = new BaseIdResponse(ResultType.SUCCESS);
    response.setId(task.getId());
    response.setHeadImage(task.getHeadImage());
    response.setUsername(task.getUsername());
    return response;
  }

  public BaseResponse deleteTask(DeleteTaskRequest request) {
    User user = UserInfo.getUser();
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Task task = taskDao.get(request.getId());
    if (task == null) {
      return Resp.success;
    }
    if (task.getType() == 2) {
      User taskUser = task.getUser();
      if (!taskUser.getTeam().getId().equals(user.getTeam().getId())) {
        return Resp.noAuth;
      }
    } else if (!task.getUser().getId().equals(user.getId())) {
      return new BaseResponse(ResultType.NO_AUTH);
    }
    taskDao.delete(task);
    return response;
  }

  public BaseResponse updateTask(UpdateTaskRequest request) {
    User user = UserInfo.getUser();
    Task task = taskDao.get(request.getId());
    if (task.getType() == 2) {
      User taskUser = task.getUser();
      if (!taskUser.getTeam().getId().equals(user.getTeam().getId())) {
        return Resp.noAuth;
      }
    } else if (!task.getUser().getId().equals(user.getId())) {
      return new BaseResponse(ResultType.NO_AUTH);
    }
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    task.setLevel(request.getLevel());
    task.setType(request.getType());
    task.setTitle(request.getTitle());
    task.setContent(request.getContent());
    task.setStatus(request.getStatus());
    task.setDate(request.getDate());
    task.setStartTime(request.getStartTime());
    task.setEndTime(request.getEndTime());
    task.setAuth(request.getAuth());
    task.setCardId(request.getCardId());
    if (request.getOwnerId() != null) {
      User owner = new User();
      owner.setId(request.getOwnerId());
      task.setOwner(owner);
    }
    if (request.getJoinIds() != null) {
      if (request.getJoinIds().endsWith(",")) {
        request.setJoinIds(request.getJoinIds().substring(0, request.getJoinIds().length() - 1));
      }
      String joinNames = "";
      Set<User> joins = new HashSet<User>();
      if (!request.getJoinIds().equals("")) {
        for (String idStr : request.getJoinIds().split(",")) {
          User join = userDao.get(Integer.valueOf(idStr));
          joins.add(join);
          joinNames += join.getName() + "、";
          messageService.downAcceptMessage(user, join, task);

        }
        if (joinNames.length() > 0) {
          joinNames = joinNames.substring(0, joinNames.length() - 1);
        }
      }
      task.setTaskJoins(joins);
      task.setJoinNames(joinNames);
      if (joins.size() > 0) {
        task.setDownAccept(1);
      } else {
        task.setDownAccept(0);
      }
    }
    if (request.getJoinTeamIds() != null) {
      if (request.getJoinTeamIds().endsWith(",")) {
        request.setJoinIds(request.getJoinTeamIds().substring(0, request.getJoinTeamIds().length() - 1));
      }
      if (!request.getJoinTeamIds().equals("")) {
        for (String idStr : request.getJoinTeamIds().split(",")) {
          Team dep = new Team();
          dep.setId(Integer.parseInt(idStr));
          List<User> users = userDao.getAllOfDep(dep);
          if (users != null && users.size() > 0) {
            task.getTaskJoins().addAll(users);
          }
        }
      }
    }
    taskDao.update(task);
    return response;
  }

  public BaseResponse showTask(ShowTaskRequest request) {
    List<Object> params = new ArrayList<Object>();
    StringBuilder sql = new StringBuilder("from Task t where 1=1 and type = ? and user = ?");
    params.add(request.getType());
    params.add(UserInfo.getUser());
    if (request.getDate() != null) {
      sql.append("and date = ?");
      params.add(request.getDate());
    }
    //日程未完成
    if (request.getType() == 1
        && request.getDate() != null
        && request.getDate().equals(DateUtil.getDateStr(new Date()))) {
      sql.append("or (status = 0 and user = ?)");
      params.add(UserInfo.getUser());
    }
    //收纳箱参与人
    if (request.getType() == 0) {
      sql.append("or id in (select tj.task.id from TaskJoin tj where tj.joinUser = ?)");
      params.add(UserInfo.getUser());
    }
    sql.append("order by date desc");
    List<Task> dbTasks = taskDao.find(sql.toString(), params.toArray());

    ShowTaskResponse response = new ShowTaskResponse(ResultType.SUCCESS);
    if (dbTasks != null) {
      List<TaskPojo> list = new ArrayList<TaskPojo>();
      for (Task dbTask : dbTasks) {
        TaskPojo pojo = new TaskPojo();
        TransUtil.trans(dbTask, pojo);
        if (dbTask.getDownAccept() != null && dbTask.getDownAccept() == 1) {
          if (dbTask.getUser().getId().equals(UserInfo.getUser().getId())) {
            pojo.setUp(1);
          } else {
            pojo.setDown(1);
          }
        }
        list.add(pojo);
      }
      response.setTaskList(list);
    }
    return response;
  }

  //
  public BaseResponse detailTask(DetailTaskRequest request) {
    DetailTaskResponse response = new DetailTaskResponse(ResultType.SUCCESS);
    Task dbTask = taskDao.getDetail(request.getId());
    TaskPojo task = TransUtil.gen(dbTask, TaskPojo.class);
    UserPojo user = TransUtil.gen(dbTask.getUser(), UserPojo.class);
    UserPojo owner = TransUtil.gen(dbTask.getOwner(), UserPojo.class);
    List<UserPojo> userJoinList = new ArrayList<UserPojo>();
//    for(TaskJoin taskJoin:dbTask.getTaskJoins()) {
//      UserPojo join = new UserPojo();
//      join.setName(taskJoin.getJoinUser().getName());
//      join.setHeadImage(taskJoin.getJoinUser().getHeadImage());
//      userJoinList.add(join);
//    }
    for (User taskJoin : dbTask.getTaskJoins()) {
      UserPojo join = new UserPojo();
      join.setId(taskJoin.getId());
      join.setName(taskJoin.getName());
      join.setHeadImage(taskJoin.getHeadImage());
      join.setType("1");
      userJoinList.add(join);
    }
    List<TaskReplyPojo> taskReplyPojoList = new ArrayList<TaskReplyPojo>();
    for (TaskReply taskReply : dbTask.getTaskReplys()) {
      TaskReplyPojo taskReplyPojo = TransUtil.gen(taskReply, TaskReplyPojo.class);
      taskReplyPojoList.add(taskReplyPojo);
    }
    response.setTask(task);
    response.setUser(user);
    response.setOwner(owner);
    response.setJoinList(userJoinList);
    response.setReplyList(taskReplyPojoList);
    return response;
  }

  public BaseResponse showStar(ShowStarRequest request) {
    User user = UserInfo.getUser();
    List<String> dateList = taskDao.findDistinctDateOfUser(request, user);
    ShowStarResponse response = new ShowStarResponse(ResultType.SUCCESS);
    response.setDateList(dateList);
    return response;
  }

  public BaseResponse replyTask(ReplyTaskRequest request) {
    User user = UserInfo.getUser();
    TaskReply reply = new TaskReply();
    Task task = new Task();
    task.setId(request.getTaskId());
    reply.setTask(task);
    reply.setContent(request.getContent());
    reply.setCreateTime(new Date());
    reply.setUser(user);
    reply.setName(user.getName());
    reply.setHeadImage(user.getHeadImage());
    boolean result = taskReplyDao.save(reply);

    Task hostTask = taskDao.getDetail(request.getTaskId());
    User toUser = hostTask.getUser();
    if (!user.getId().equals(toUser.getId())) {
      messageService.replyTaskMessage(user, toUser, hostTask);
    }

    if (!result) {
      BaseResponse response = new BaseResponse(ResultType.ERROR);
      return response;
    }
    ReplyResponse response = new ReplyResponse(ResultType.SUCCESS);
    response.setName(user.getName());
    response.setHeadImage(user.getHeadImage());
    response.setId(reply.getId());
    return response;
  }

  /**
   * 下发任务
   *
   * @param request
   * @return
   */
  public BaseResponse assignTask(AssignTaskRequest request) {
    User user = UserInfo.getUser();
    Task task = taskDao.get(request.getId());
    if (!task.getUser().getId().equals(user.getId())) {
      return new BaseResponse(ResultType.NO_AUTH);
    }
    if (request.getOwnerId() != null) {
      User owner = userDao.get(request.getOwnerId());
      task.setOwner(owner);
    }
    if (request.getJoinIds() != null) {
      Set<User> joins = new HashSet<User>();
      for (Integer id : request.getJoinIds()) {
        User joinUser = new User();
        joinUser.setId(id);
        joins.add(joinUser);
      }
      task.setTaskJoins(joins);
    }
    task.setDownAccept(1);//设置下发为1
    taskDao.update(task);

    Task hostTask = taskDao.getDetail(request.getId());
    User toUser = hostTask.getUser();
    if (!user.getId().equals(toUser.getId())) {
      messageService.downAcceptMessage(user, toUser, hostTask);
    }
    return Resp.success;
  }
}
