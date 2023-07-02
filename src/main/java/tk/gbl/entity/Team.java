package tk.gbl.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 团队/同事
 *
 * Date: 2015/3/31
 * Time: 22:09
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "team")
public class Team extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  @Column(name = "sso_id")
  private String ssoId;

  /**
   * 类型 0 公司
   */
  @Column(name = "type")
  private String type;

  @Column(name = "name")
  private String name;

  @Column(name = "version")
  private String version;

  @Column(name="update_time")
  private Date updateTime;

  /**
   * 公司 如果是公司则没有为空
   */
  @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id")
  private Team company;

  /**
   * 父部门 如果是公司则没有为空
   */
  @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "father_id")
  private Team father;

  /**
   * 部门的老大
   */
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  private User owner;

  /**
   * 子部门
   */
  @OneToMany(mappedBy = "father", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Team> children = new HashSet<Team>();

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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Team getFather() {
    return father;
  }

  public void setFather(Team father) {
    this.father = father;
  }

  public Set<Team> getChildren() {
    return children;
  }

  public void setChildren(Set<Team> children) {
    this.children = children;
  }

  public Team getCompany() {
    return company;
  }

  public void setCompany(Team company) {
    this.company = company;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }
}
