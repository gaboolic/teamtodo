package gbl.pojo.request;

import tk.gbl.anno.ValidField;

/**
 * Date: 2015/3/4
 * Time: 11:23
 *
 * @author Tian.Dong
 */
public class DeleteMessageRequest {
  @ValidField
  private Integer mid;

  public Integer getMid() {
    return mid;
  }

  public void setMid(Integer mid) {
    this.mid = mid;
  }
}
