package tk.gbl.entity.board;

import tk.gbl.entity.BaseEntity;
import tk.gbl.entity.Team;
import tk.gbl.entity.User;

import javax.persistence.*;

/**
 * 卡片 (实际上是白板的todo doing cc testing pass每个栏)
 * <p/>
 * Date: 2015/3/31
 * Time: 22:11
 *
 * @author Tian.Dong
 */
@Entity
@Table(name = "card")
public class Card extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  @Column(name = "name")
  String name;

  /**
   * 所属用户(创建人)
   */
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  User user;

  /**
   * 所属team
   */
  @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  /**
   * 所属看板
   */
  @ManyToOne(targetEntity = Board.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  /**
   * 序号
   */
  @Column(name="seq_no")
  private Integer seqNo;

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

  public Board getBoard() {
    return board;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public Integer getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(Integer seqNo) {
    this.seqNo = seqNo;
  }
}
