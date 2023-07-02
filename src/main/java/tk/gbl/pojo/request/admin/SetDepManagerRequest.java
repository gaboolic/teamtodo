package tk.gbl.pojo.request.admin;

import tk.gbl.agent.request.BaseRequest;

/**
 * Date: 2015/6/17
 * Time: 17:22
 *
 * @author Tian.Dong
 */
public class SetDepManagerRequest extends BaseRequest{
  private Integer depId;
  private Integer userId;

  public Integer getDepId() {
    return depId;
  }

  public void setDepId(Integer depId) {
    this.depId = depId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }
}
