package gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.entity.Reply;

import java.util.List;

/**
 * Date: 2015/3/4
 * Time: 10:56
 *
 * @author Tian.Dong
 */
public class ReplyListResponse extends BaseResponse{
  List<Reply> replyList;

  public ReplyListResponse(ResultType type) {
    super(type);
  }

  public List<Reply> getReplyList() {
    return replyList;
  }

  public void setReplyList(List<Reply> replyList) {
    this.replyList = replyList;
  }
}
