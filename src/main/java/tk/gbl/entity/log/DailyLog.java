package tk.gbl.entity.log;

import tk.gbl.entity.BaseEntity;
import tk.gbl.entity.Team;
import tk.gbl.entity.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 日志
 * <p/>
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

  @Column(name = "username")
  String username;

  @Column(name = "head_image")
  String headImage;

  @Column(name = "star")
  String star;

  /**
   * 用户所属Team
   */
  @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  Team team;

  /**
   * 标题
   */
  @Column(name = "title")
  String title;

  /**
   * 备注 内容
   */
  @Column(name = "content",length = 3000)
  String content;

  /**
   * 阅读量
   */
  @Column(name = "view_count")
  Integer viewCount;

  /**
   * 附件数
   */
  @Column(name = "file_count")
  Integer fileCount = 0;

  @Column(name = "at_names")
  String atNames;

  /**
   * 访问列表
   */
  @OneToMany(mappedBy = "dailyLog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<DailyLogVisit> visits = new HashSet<DailyLogVisit>();

  /**
   * 评论列表
   */
  @OneToMany(mappedBy = "dailyLog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @OrderBy("id DESC")
  private Set<DailyLogReply> replies = new HashSet<DailyLogReply>();

  /**
   * 艾特列表
   */
  @OneToMany(mappedBy = "dailyLog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<DailyLogAt> ats = new HashSet<DailyLogAt>();

  /**
   * 附件列表
   */
  @OneToMany(mappedBy = "dailyLog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<DailyLogFile> files;

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

  public Set<DailyLogVisit> getVisits() {
    return visits;
  }

  public void setVisits(Set<DailyLogVisit> visits) {
    this.visits = visits;
  }

  public Integer getViewCount() {
    if (viewCount == null) {
      return 0;
    }
    return viewCount;
  }

  public void setViewCount(Integer viewCount) {
    this.viewCount = viewCount;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getHeadImage() {
    return headImage;
  }

  public void setHeadImage(String headImage) {
    this.headImage = headImage;
  }

  public String getStar() {
    return star;
  }

  public void setStar(String star) {
    this.star = star;
  }

  public Set<DailyLogReply> getReplies() {
    return replies;
  }

  public void setReplies(Set<DailyLogReply> replies) {
    this.replies = replies;
  }

  public Set<DailyLogAt> getAts() {
    return ats;
  }

  public void setAts(Set<DailyLogAt> ats) {
    this.ats = ats;
  }

  public Set<DailyLogFile> getFiles() {
    return files;
  }

  public void setFiles(Set<DailyLogFile> files) {
    this.files = files;
  }

  public Integer getFileCount() {
    return fileCount;
  }

  public void setFileCount(Integer fileCount) {
    this.fileCount = fileCount;
  }

  public String getAtNames() {
    return atNames;
  }

  public void setAtNames(String atNames) {
    this.atNames = atNames;
  }
}
