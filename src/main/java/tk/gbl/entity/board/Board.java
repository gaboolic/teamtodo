package tk.gbl.entity.board;

import tk.gbl.entity.Team;
import tk.gbl.entity.User;

import javax.persistence.*;

/**
 * 看板
 * <p/>
 * Date: 2015/3/31
 * Time: 22:06
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "board")
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  /**
   * 看板名字
   */
  @Column(name = "name")
  String name;

  /**
   * 所属用户(创建人)
   */
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  User user;

  @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  /**
   * 权限
   * -1 仅自己
   * 0 任何人
   */
  @Column(name = "auth")
  private String auth;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name != null)
      this.name = name;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public String getAuth() {
    return auth;
  }

  public void setAuth(String auth) {
    if (auth != null)
      this.auth = auth;
  }
}
