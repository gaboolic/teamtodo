package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.TaskDao;
import tk.gbl.entity.Task;
import tk.gbl.pojo.TaskPojo;
import tk.gbl.pojo.request.AddTaskRequest;
import tk.gbl.pojo.request.DeleteTaskRequest;
import tk.gbl.pojo.request.ShowTaskRequest;
import tk.gbl.pojo.request.UpdateTaskRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.pojo.response.ShowTaskResponse;
import tk.gbl.util.TransUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
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

  public BaseResponse addTask(AddTaskRequest request) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    return response;
  }

  public BaseResponse deleteTask(DeleteTaskRequest request) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
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
    if(request.getDate()!=null) {
      sql.append("and date = ?");
      params.add(request.getDate());
    }
    fillSqlAndParamsByRequest(sql, params, request);
    List<Task> dbTasks = taskDao.find(sql.toString(),params.toArray());

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

  private void fillSqlAndParamsByRequest(StringBuilder sql, List<Object> params, ShowTaskRequest request) {



  }
}
