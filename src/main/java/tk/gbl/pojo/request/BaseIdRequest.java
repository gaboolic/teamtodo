package tk.gbl.pojo.request;

import tk.gbl.anno.ValidField;

/**
 * Date: 2015/4/10
 * Time: 15:51
 *
 * @author Tian.Dong
 */
public class BaseIdRequest extends BaseRequest {
  @ValidField
  private Integer id;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
