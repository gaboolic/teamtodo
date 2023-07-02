package tk.gbl.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 消息
 * <p/>
 * Date: 2015/4/1
 * Time: 11:27
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "message")
public class Message extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  /**
   * 消息发送人
   */
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "from_id")
  User from;

  @Column(name = "from_user_name")
  String fromUserName;

  /**
   * 消息接收人
   */
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  User user;

  @Column(name = "to_user_name")
  String toUserName;

  /**
   * 类型
   * task
   * board
   * log
   *
   * 1艾特
   * 2回复 日志
   * 3回复 任务
   * 4分配 任务
   * 5参与任务
   */
  @Column(name = "type")
  String type;

  @Column(name = "type_name")
  String typeName;

  @Column(name = "subject_id")
  Integer subjectId;

  /**
   * 0 未读
   * 1
   * 2 已读
   */
  @Column(name = "status")
  String status = "0";

  @Column(name = "`desc`")
  String desc;

  @Column(name = "`full_desc`")
  String fullDesc;

  @Column(name = "link")
  String link;

  @Column(name = "pc_link")
  String pcLink;

  @Column(name = "app_link")
  String appLink;

  @Column(name = "uuid")
  String uuid;

  @Column(name = "create_time")
  Date createTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public User getFrom() {
    return from;
  }

  public void setFrom(User from) {
    this.from = from;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getFromUserName() {
    return fromUserName;
  }

  public void setFromUserName(String fromUserName) {
    this.fromUserName = fromUserName;
  }

  public String getToUserName() {
    return toUserName;
  }

  public void setToUserName(String toUserName) {
    this.toUserName = toUserName;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getFullDesc() {
    return fullDesc;
  }

  public void setFullDesc(String fullDesc) {
    this.fullDesc = fullDesc;
  }

  public Integer getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(Integer subjectId) {
    this.subjectId = subjectId;
  }

  public String getPcLink() {
    return pcLink;
  }

  public void setPcLink(String pcLink) {
    this.pcLink = pcLink;
  }

  public String getAppLink() {
    return appLink;
  }

  public void setAppLink(String appLink) {
    this.appLink = appLink;
  }
}
