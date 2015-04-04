package tk.gbl.entity;

import javax.persistence.*;

/**
 * Date: 2015/4/1
 * Time: 11:31
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "task_join")
public class TaskJoin extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  /**
   * 任务
   */
  @ManyToOne(targetEntity = Task.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "task_id")
  Task task;

  /**
   * 参与人
   */
  @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  User joinUser;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  public User getJoinUser() {
    return joinUser;
  }

  public void setJoinUser(User joinUser) {
    this.joinUser = joinUser;
  }
}
