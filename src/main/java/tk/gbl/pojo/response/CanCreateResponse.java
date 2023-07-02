package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;

/**
 * Date: 2015/6/4
 * Time: 20:17
 *
 * @author Tian.Dong
 */
public class CanCreateResponse extends BaseResponse {
  public CanCreateResponse(ResultType type) {
    super(type);
  }

  private Integer isCanCreate;

  public Integer getIsCanCreate() {
    return isCanCreate;
  }

  public void setIsCanCreate(Integer isCanCreate) {
    this.isCanCreate = isCanCreate;
  }
}
