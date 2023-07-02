package tk.gbl.entity;

import javax.persistence.*;

/**
 * Date: 2015/5/21
 * Time: 11:58
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "user_auth")
public class UserAuth extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "auth_user_id")
  private User authUser;

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

  public User getAuthUser() {
    return authUser;
  }

  public void setAuthUser(User authUser) {
    this.authUser = authUser;
  }
}
