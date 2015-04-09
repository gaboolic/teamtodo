package tk.gbl.entity.log;

import tk.gbl.entity.BaseEntity;
import tk.gbl.entity.User;

import javax.persistence.*;

/**
 * 日志
 *
 * Date: 2015/3/31
 * Time: 21:50
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "daily_log")
public class DailyLog extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

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
   * 标题
   */
  @Column(name = "title")
  String title;

  /**
   * 备注 内容
   */
  @Column(name = "content")
  String content;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
