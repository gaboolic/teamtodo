package tk.gbl.pojo.request;

import tk.gbl.anno.ValidField;

/**
 * Date: 2015/3/4
 * Time: 11:06
 *
 * @author Tian.Dong
 */
public class AddReplyRequest {
  @ValidField
  private Integer tid;

  @ValidField
  private String content;

  public Integer getTid() {
    return tid;
  }

  public void setTid(Integer tid) {
    this.tid = tid;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
