package tk.gbl.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Date: 2015/4/8
 * Time: 14:41
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "task_reply")
public class TaskReply {
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
   * 评论人
   */
  @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  User user;

  /**
   * 评论人name
   */
  @Column(name = "name")
  private String name;

  /**
   * 评论人头像
   */
  @Column(name = "head_image")
  private String headImage;

  /**
   * 评论的内容
   */
  @Column(name = "content")
  String content;

  /**
   * 创建时间
   */
  @Column(name = "create_time")
  Date createTime;


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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHeadImage() {
    return headImage;
  }

  public void setHeadImage(String headImage) {
    this.headImage = headImage;
  }
}
