package tk.gbl.entity.log;

import tk.gbl.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Date: 2015/5/12
 * Time: 14:53
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "daily_log_file")
public class DailyLogFile extends BaseEntity {
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
   * 创建时间
   */
  @Column(name = "create_time")
  Date createTime;

  /**
   * 原始文件名
   */
  @Column(name = "name")
  String name;

  /**
   * 路径
   */
  @Column(name = "path")
  String path;

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

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
