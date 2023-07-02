package tk.gbl.entity.admin;

import tk.gbl.entity.BaseEntity;
import tk.gbl.entity.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Date: 2015/5/18
 * Time: 13:13
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "admin_login")
public class AdminLogin extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "admin_id")
  User admin;

  String username;
  String token;

  @Column(name="ip")
  String ip;

  @Column(name="create_time")
  Date createTime;

  /**
   * 1 账号密码
   * 2 im
   */
  @Column(name="type")
  String type;

  @Column(name="is_success")
  Integer isSuccess;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getIsSuccess() {
    return isSuccess;
  }

  public void setIsSuccess(Integer isSuccess) {
    this.isSuccess = isSuccess;
  }

  public User getAdmin() {
    return admin;
  }

  public void setAdmin(User admin) {
    this.admin = admin;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
