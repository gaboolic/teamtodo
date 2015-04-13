package tk.gbl.pojo.request.card;

import tk.gbl.pojo.request.BaseRequest;

/**
 * Date: 2015/4/13
 * Time: 14:17
 *
 * @author Tian.Dong
 */
public class AllCardRequest extends BaseRequest {
  private Integer boardId;

  public Integer getBoardId() {
    return boardId;
  }

  public void setBoardId(Integer boardId) {
    this.boardId = boardId;
  }
}
