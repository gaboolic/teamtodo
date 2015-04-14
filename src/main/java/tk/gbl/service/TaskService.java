package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.Resp;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.TaskDao;
import tk.gbl.dao.TaskReplyDao;
import tk.gbl.dao.UserDao;
import tk.gbl.entity.Task;
import tk.gbl.entity.TaskReply;
import tk.gbl.entity.User;
import tk.gbl.pojo.TaskPojo;
import tk.gbl.pojo.TaskReplyPojo;
import tk.gbl.pojo.UserPojo;
import tk.gbl.pojo.request.ShowStarRequest;
import tk.gbl.pojo.request.task.*;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.pojo.response.DetailTaskResponse;
import tk.gbl.pojo.response.ShowStarResponse;
import tk.gbl.pojo.response.ShowTaskResponse;
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

  public BaseResponse addTask(AddTaskRequest request) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Task task = new Task();
    task.setLevel(request.getLevel());
    task.setType(request.getType());
    task.setDate(request.getDate());
    task.setUser(UserInfo.getUser());

    task.setTitle(request.getTitle());
    task.setContent(request.getContent());
    taskDao.save(task);
    return response;
  }

  public BaseResponse deleteTask(DeleteTaskRequest request) {
    User user = UserInfo.getUser();
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Task task = taskDao.get(request.getId());
    if (!task.getUser().getId().equals(user.getId())) {
      return new BaseResponse(ResultType.NO_AUTH);
    }
    taskDao.delete(task);
    return response;
  }

  public BaseResponse updateTask(UpdateTaskRequest request) {
    User user = UserInfo.getUser();
    Task task = taskDao.get(request.getId());
    if (!task.getUser().getId().equals(user.getId())) {
      return new BaseResponse(ResultType.NO_AUTH);
    }
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    task.setLevel(request.getLevel());
    task.setType(request.getType());
    task.setTitle(request.getTitle());
    task.setContent(request.getContent());
    task.setStatus(request.getStatus());
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
    if (request.getType() == 0) {
      sql.append("or id in (select tj.task.id from TaskJoin tj where tj.joinUser = ?)");
      params.add(UserInfo.getUser());
    }
    List<Task> dbTasks = taskDao.find(sql.toString(), params.toArray());

    ShowTaskResponse response = new ShowTaskResponse(ResultType.SUCCESS);
    if (dbTasks != null) {
      List<TaskPojo> list = new ArrayList<TaskPojo>();
      for (Task dbTask : dbTasks) {
        TaskPojo pojo = new TaskPojo();
        TransUtil.trans(dbTask, pojo);
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
    List<UserPojo> userJoinList = new ArrayList<UserPojo>();
//    for(TaskJoin taskJoin:dbTask.getTaskJoins()) {
//      UserPojo join = new UserPojo();
//      join.setName(taskJoin.getJoinUser().getName());
//      join.setHeadImage(taskJoin.getJoinUser().getHeadImage());
//      userJoinList.add(join);
//    }
    for (User taskJoin : dbTask.getTaskJoins()) {
      UserPojo join = new UserPojo();
      join.setName(taskJoin.getName());
      join.setHeadImage(taskJoin.getHeadImage());
      userJoinList.add(join);
    }
    List<TaskReplyPojo> taskReplyPojoList = new ArrayList<TaskReplyPojo>();
    for (TaskReply taskReply : dbTask.getTaskReplys()) {
      TaskReplyPojo taskReplyPojo = TransUtil.gen(taskReply, TaskReplyPojo.class);
      taskReplyPojoList.add(taskReplyPojo);
    }
    response.setTask(task);
    response.setUser(user);
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
    if (!result) {
      BaseResponse response = new BaseResponse(ResultType.ERROR);
      return response;
    }
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    return response;
  }

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
    taskDao.update(task);
    return Resp.success;
  }
}
