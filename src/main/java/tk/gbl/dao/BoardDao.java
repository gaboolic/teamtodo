package tk.gbl.dao;

import org.springframework.stereotype.Repository;
import tk.gbl.entity.Team;
import tk.gbl.entity.board.Board;

import java.util.List;

/**
 * Date: 2015/4/7
 * Time: 11:30
 *
 * @author Tian.Dong
 */
@Repository
public class BoardDao extends SuperDao<Board> {
  public List<Board> allBoardOfTeam(Team team) {
    return find("from Board b where b.team = ? ",team);
  }
}
