package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.ResultType;
import tk.gbl.pojo.request.AddTaskRequest;
import tk.gbl.pojo.request.DeleteTaskRequest;
import tk.gbl.pojo.request.UpdateTaskRequest;
import tk.gbl.pojo.response.BaseResponse;

/**
 * Date: 2015/4/1
 * Time: 15:11
 *
 * @author Tian.Dong
 */
@Service
public class TaskService {
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
}
