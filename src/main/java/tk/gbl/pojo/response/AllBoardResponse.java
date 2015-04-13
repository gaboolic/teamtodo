package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.BoardPojo;

import java.util.List;

/**
 * Date: 2015/4/13
 * Time: 14:12
 *
 * @author Tian.Dong
 */
public class AllBoardResponse extends BaseResponse {
  List<BoardPojo> boardList;

  public AllBoardResponse(ResultType type) {
    super(type);
  }

  public List<BoardPojo> getBoardList() {
    return boardList;
  }

  public void setBoardList(List<BoardPojo> boardList) {
    this.boardList = boardList;
  }
}
