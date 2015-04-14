package tk.gbl.entity;

import javax.persistence.*;

/**
 * 消息
 *
 * Date: 2015/4/1
 * Time: 11:27
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "message")
public class Message extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  /**
   * 消息接收人
   */
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  User user;

  /**
   * 类型
   * task
   * board
   * log
   */
  String type;
}
