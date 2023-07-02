package tk.gbl.entity.log;

import tk.gbl.entity.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Date: 2015/4/10
 * Time: 17:47
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "daily_log_reply")
public class DailyLogReply {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  /**
   * 日志
   */
  @ManyToOne(targetEntity = DailyLog.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "daily_log_id")
  DailyLog dailyLog;

  /**
   * 评论人
   */
  @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  User user;

  /**
   * 创建时间
   */
  @Column(name = "create_time")
  Date createTime;

  /**
   * 名字
   */
  @Column(name = "name")
  String name;

  /**
   * 头像
   */
  @Column(name = "head_image")
  String headImage;

  /**
   * 评论的内容
   */
  @Column(name = "content",length = 3000)
  String content;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public DailyLog getDailyLog() {
    return dailyLog;
  }

  public void setDailyLog(DailyLog dailyLog) {
    this.dailyLog = dailyLog;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
