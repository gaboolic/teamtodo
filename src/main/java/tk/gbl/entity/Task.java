package tk.gbl.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 任务(日程、收纳箱、看板的任务模型)
 * <p/>
 * Date: 2015/3/31
 * Time: 22:47
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "task")
public class Task extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  /**
   * 级别
   * 紧急 重要
   * 00 不紧急不重要
   * 01 不紧急重要
   * 10 紧急不重要
   * 11 紧急重要
   */
  @Column(name = "level")
  Integer level;

  /**
   * 类型
   * 收纳箱 0
   * 日程   1
   * 看板   2
   */
  @Column(name = "type")
  Integer type;

  /**
   * 所属日期
   */
  @Column(name = "date")
  String date;

  /**
   * 所属用户(创建人)
   */
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  User user;

  /**
   * 负责人
   */
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  User owner;

//  /**
//   * 参与人
//   */
//  @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//  private Set<TaskJoin> taskJoins = new HashSet<TaskJoin>();

  /**
   * 参与人
   */

  @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  @JoinTable(name = "task_join",
      joinColumns = { @JoinColumn(name = "task_id") },
      inverseJoinColumns = { @JoinColumn(name = "user_id") })
  private Set<User> taskJoins = new HashSet<User>();

  /**
   * 评论
   */
  @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<TaskReply> taskReplys = new HashSet<TaskReply>();

  /**
   * 标题
   */
  @Column(name = "title")
  String title;

  /**
   * 备注 内容
   */
  @Column(name = "content")
  String content;

  /**
   * 状态
   *
   * 0未完成
   * 1完成
   */
  @Column(name = "status")
  String status = "0";

  /**
   * 如果是卡片上的任务
   * 则有负责人 参与人 开始时间 结束时间
   */
  @Column(name="card_id")
  Integer cardId;

  /**
   * 开始时间
   */
  @Column(name = "start_time")
  String startTime;

  /**
   * 结束时间
   */
  @Column(name = "end_time")
  String endTime;

  /**
   * 序号
   */
  @Column(name="seq_no")
  private Integer seqNo;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    if (level != null)
      this.level = level;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    if (type != null)
      this.type = type;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    if (date != null)
      this.date = date;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    if (user != null)
      this.user = user;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

//  public Set<TaskJoin> getTaskJoins() {
//    return taskJoins;
//  }
//
//  public void setTaskJoins(Set<TaskJoin> taskJoins) {
//    this.taskJoins = taskJoins;
//  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    if (title != null)
      this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    if (content != null)
      this.content = content;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    if (status != null)
      this.status = status;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public Set<TaskReply> getTaskReplys() {
    return taskReplys;
  }

  public void setTaskReplys(Set<TaskReply> taskReplys) {
    this.taskReplys = taskReplys;
  }

  public Integer getCardId() {
    return cardId;
  }

  public void setCardId(Integer cardId) {
    this.cardId = cardId;
  }

  public Integer getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(Integer seqNo) {
    this.seqNo = seqNo;
  }

  public Set<User> getTaskJoins() {
    return taskJoins;
  }

  public void setTaskJoins(Set<User> taskJoins) {
    this.taskJoins = taskJoins;
  }
}
