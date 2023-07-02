package tk.gbl.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户-部门 关联表
 *
 * Date: 2015/6/16
 * Time: 18:20
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "user_team")
public class UserTeam {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "dep_id")
  private Team dep;

  @Column(name = "version")
  private String version;

  @Column(name="update_time")
  private Date updateTime;

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

  public Team getDep() {
    return dep;
  }

  public void setDep(Team dep) {
    this.dep = dep;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}
