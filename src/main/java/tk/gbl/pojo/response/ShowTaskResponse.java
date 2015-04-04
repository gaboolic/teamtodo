package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.TaskPojo;

import java.util.List;

/**
 * Date: 2015/4/1
 * Time: 18:29
 *
 * @author Tian.Dong
 */
public class ShowTaskResponse extends BaseResponse {
  List<TaskPojo> taskList;

  public ShowTaskResponse(ResultType type) {
    super(type);
  }

  public List<TaskPojo> getTaskList() {
    return taskList;
  }

  public void setTaskList(List<TaskPojo> taskList) {
    this.taskList = taskList;
  }
}
