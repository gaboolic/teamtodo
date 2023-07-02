package tk.gbl.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

  /**
   * 姓名
   */
  @Column(name = "name")
  private String name;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "head_image")
  private String headImage;

  @Column(name = "sex_type")
  private String sexType;

  @Column(name = "sex")
  private String sex;

  /**
   * 登录名
   */
  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  /**
   * 用户类型 普通 经理1 超级2
   */
  @Column(name = "type")
  private String type;

  /**
   * 是否是经理
   */
  @Column(name = "is_manager")
  private Integer isManager;

  /**
   * 用户类型 普通 经理
   */
  @Column(name = "type_name")
  private String typeName;

  @Column(name = "dep_name")
  private String depName;

  @Column(name = "team_name")
  private String teamName;

  /**
   * 单点登录token
   */
  @Column(name = "token")
  private String token;

  /**
   * 所在大组(公司)
   */
  @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  /**
   * 所在小组(部门)
   * 如空 则是公司直属
   */
  @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "dep_id")
  private Team dep;

  /**
   * 日志权限
   * -1 仅自己
   * 0 任何人
   * 其他如 4,5,6
   */
  @Column(name = "auth")
  String auth;

  @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  @JoinTable(name = "user_auth",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "auth_user_id")})
//  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<User> userAuthList = new HashSet<User>();

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

  public String getAuth() {
    return auth;
  }

  public void setAuth(String auth) {
    if (auth != null)
      this.auth = auth;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Team getDep() {
    return dep;
  }

  public void setDep(Team dep) {
    this.dep = dep;
  }

  public Set<User> getUserAuthList() {
    return userAuthList;
  }

  public void setUserAuthList(Set<User> userAuthList) {
    this.userAuthList = userAuthList;
  }

  public String getSexType() {
    return sexType;
  }

  public void setSexType(String sexType) {
    this.sexType = sexType;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String getDepName() {
    return depName;
  }

  public void setDepName(String depName) {
    this.depName = depName;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getIsManager() {
    return isManager;
  }

  public void setIsManager(Integer isManager) {
    this.isManager = isManager;
  }
}
