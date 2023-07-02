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

  /**
   * 创建人
   */
  private UserPojo user;

  /**
   * 负责人
   */
  private UserPojo owner;

  /**
   * 参与人
   */
  private List<UserPojo> joinList;

  /**
   * 评论列表
   */
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

  public UserPojo getOwner() {
    return owner;
  }

  public void setOwner(UserPojo owner) {
    this.owner = owner;
  }
}
