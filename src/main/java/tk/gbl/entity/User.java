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


  private String ssoId;

  private String token;
}
