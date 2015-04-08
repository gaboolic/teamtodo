package tk.gbl.pojo.request.task;

import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.BaseRequest;

/**
 * Date: 2015/4/8
 * Time: 18:33
 *
 * @author Tian.Dong
 */
public class ReplyTaskRequest extends BaseRequest{
  @ValidField
  private Integer taskId;

  @ValidField
  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getTaskId() {
    return taskId;
  }

  public void setTaskId(Integer taskId) {
    this.taskId = taskId;
  }
}
