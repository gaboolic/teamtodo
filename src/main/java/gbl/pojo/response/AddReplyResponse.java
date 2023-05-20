package gbl.pojo.response;

import tk.gbl.constants.ResultType;

/**
 * Date: 2015/3/4
 * Time: 11:06
 *
 * @author Tian.Dong
 */
public class AddReplyResponse extends BaseResponse {
  public AddReplyResponse(ResultType type) {
    super(type);
  }

  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
