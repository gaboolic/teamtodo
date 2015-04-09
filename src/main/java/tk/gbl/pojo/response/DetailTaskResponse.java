package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.TaskPojo;
import tk.gbl.pojo.TaskReplyPojo;
import tk.gbl.pojo.UserPojo;

import java.util.List;

/**
 * Date: 2015/4/8
 * Time: 18:59
 *
 * @author Tian.Dong
 */
public class DetailTaskResponse extends BaseResponse {
  private TaskPojo task;

  private UserPojo user;

  private List<UserPojo> joinList;

  private List<TaskReplyPojo> replyList;


  public DetailTaskResponse(ResultType type) {
    super(type);
  }

  public TaskPojo getTask() {
    return task;
  }

  public void setTask(TaskPojo task) {
    this.task = task;
  }

  public UserPojo getUser() {
    return user;
  }

  public void setUser(UserPojo user) {
    this.user = user;
  }

  public List<UserPojo> getJoinList() {
    return joinList;
  }

  public void setJoinList(List<UserPojo> joinList) {
    this.joinList = joinList;
  }

  public List<TaskReplyPojo> getReplyList() {
    return replyList;
  }

  public void setReplyList(List<TaskReplyPojo> replyList) {
    this.replyList = replyList;
  }
}
