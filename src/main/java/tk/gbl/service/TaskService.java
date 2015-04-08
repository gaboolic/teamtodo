package tk.gbl.service;

import org.hibernate.validator.jtype.ClassSerializer;
import org.springframework.stereotype.Service;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.TaskDao;
import tk.gbl.dao.TaskReplyDao;
import tk.gbl.entity.Task;
import tk.gbl.entity.TaskReply;
import tk.gbl.entity.User;
import tk.gbl.pojo.TaskPojo;
import tk.gbl.pojo.request.task.*;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.pojo.response.ShowStarResponse;
import tk.gbl.pojo.response.ShowTaskResponse;
import tk.gbl.util.TransUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
  TaskReplyDao taskReplyDao;

  public BaseResponse addTask(AddTaskRequest request) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Task task = new Task();
    task.setLevel(request.getLevel());
    task.setType(request.getType());
    task.setDate(request.getDate());

    task.setTitle(request.getTitle());
    task.setContent(request.getContent());
    taskDao.save(task);
    return response;
  }

  public BaseResponse deleteTask(DeleteTaskRequest request) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    Task task = new Task();
    task.setId(request.getId());
    taskDao.delete(task);
    return response;
  }

  public BaseResponse updateTask(UpdateTaskRequest request) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    return response;
  }

  public BaseResponse showTask(ShowTaskRequest request) {
    List<Object> params = new ArrayList<Object>();
    StringBuilder sql = new StringBuilder("from Task t where 1=1 and type = ? ");
    params.add(request.getType());
    if (request.getDate() != null) {
      sql.append("and date = ?");
      params.add(request.getDate());
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


  public ClassSerializer detailTask(DetailTaskRequest request) {
    return null;
  }

  public BaseResponse showStar(ShowStarRequest request, HttpSession session) {
    User user = (User) session.getAttribute("user");
    List<String> dateList = taskDao.findDistinctDateOfUser(request, user);
    ShowStarResponse response = new ShowStarResponse(ResultType.SUCCESS);
    response.setDateList(dateList);
    return response;
  }

  public BaseResponse replyTask(ReplyTaskRequest request, HttpSession session) {
    TaskReply reply = new TaskReply();
    Task task = new Task();
    task.setId(request.getTaskId());
    reply.setTask(task);
    reply.setContent(request.getContent());
    reply.setCreateTime(new Date());
    reply.setUser((User) session.getAttribute("user"));
    boolean result = taskReplyDao.save(reply);
    if (!result) {
      BaseResponse response = new BaseResponse(ResultType.ERROR);
      return response;
    }
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    return response;
  }
}
