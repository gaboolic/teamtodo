package gbl.pojo.response;

import tk.gbl.constants.ResultType;

/**
 * Date: 2015/1/20
 * Time: 15:08
 *
 * @author Tian.Dong
 */
public class AddMessageResponse extends BaseResponse {
  public AddMessageResponse(ResultType type) {
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
