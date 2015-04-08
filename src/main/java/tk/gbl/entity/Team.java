package tk.gbl.entity;

import javax.persistence.*;

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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
