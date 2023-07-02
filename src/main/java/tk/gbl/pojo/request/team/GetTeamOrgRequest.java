package tk.gbl.pojo.request.team;


import tk.gbl.pojo.request.BaseRequest;

/**
 * Date: 2015/4/26
 * Time: 21:48
 *
 * @author Tian.Dong
 */
public class GetTeamOrgRequest extends BaseRequest {
  private Integer depId;

  public Integer getDepId() {
    return depId;
  }

  public void setDepId(Integer depId) {
    this.depId = depId;
  }
}
