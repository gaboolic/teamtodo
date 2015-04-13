package tk.gbl.pojo.request.card;

import tk.gbl.pojo.request.BaseIdRequest;

/**
 * Date: 2015/4/6
 * Time: 10:16
 *
 * @author Tian.Dong
 */
public class UpdateCardRequest extends BaseIdRequest {
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
