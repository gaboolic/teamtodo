package tk.gbl.pojo.request;

import tk.gbl.anno.ValidField;

/**
 * Date: 2014/12/20
 * Time: 22:06
 *
 * @author Tian.Dong
 */
public class AddMessageRequest {
  @ValidField
  private String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
