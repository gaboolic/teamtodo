package tk.gbl.entity;

import javax.persistence.*;

/**
 * Date: 2015/4/2
 * Time: 17:27
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "user")
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  @Column(name = "sso_id")
  private String ssoId;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  /**
   * 单点登录token
   */
  @Column(name = "token")
  private String token;

  @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSsoId() {
    return ssoId;
  }

  public void setSsoId(String ssoId) {
    this.ssoId = ssoId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
