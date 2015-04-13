package tk.gbl.pojo;

import java.util.List;

/**
 * Date: 2015/4/13
 * Time: 15:30
 *
 * @author Tian.Dong
 */
public class CardPojo extends BasePojo {
  String name;

  /**
   * 序号
   */
  private Integer seqNo;

  List<TaskPojo> taskList;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(Integer seqNo) {
    this.seqNo = seqNo;
  }

  public List<TaskPojo> getTaskList() {
    return taskList;
  }

  public void setTaskList(List<TaskPojo> taskList) {
    this.taskList = taskList;
  }
}
