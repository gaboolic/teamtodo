package gbl.pojo.request;

import tk.gbl.anno.ValidField;

/**
 * Date: 2015/3/4
 * Time: 10:53
 *
 * @author Tian.Dong
 */
public class ReplyListRequest extends BaseRequest {
  @ValidField
  private Integer tid;

  public Integer getTid() {
    return tid;
  }

  public void setTid(Integer tid) {
    this.tid = tid;
  }
}
