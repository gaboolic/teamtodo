package tk.gbl.dao;

import org.springframework.stereotype.Repository;
import tk.gbl.entity.board.Board;
import tk.gbl.entity.board.Card;

import java.util.List;

/**
 * Date: 2015/4/13
 * Time: 14:18
 *
 * @author Tian.Dong
 */
@Repository
public class CardDao extends SuperDao<Card> {
  public List<Card> allCardOfBoardAndTeam(Integer boardId, Integer teamId) {
    return find("from Card c where c.board.id = ? and c.team.id = ?", boardId, teamId);
  }

  public Integer getMaxOfBoard(Board board) {
    String sql = "SELECT max(card.seqNo) FROM Card card where card.board =?";
    return findSql(sql, board).get(0) == null
        ? 0
        : (Integer) findSql(sql, board).get(0);
  }
}
