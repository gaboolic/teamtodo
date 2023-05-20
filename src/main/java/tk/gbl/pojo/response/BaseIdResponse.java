package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;

/**
 * Date: 2015/4/24
 * Time: 13:57
 *
 * @author Tian.Dong
 */
public class BaseIdResponse extends BaseResponse {
  Integer id;

  public BaseIdResponse(ResultType type) {
    super(type);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
