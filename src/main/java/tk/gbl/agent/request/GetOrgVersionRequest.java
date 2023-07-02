package tk.gbl.agent.request;

/**
 * Date: 2015/4/25
 * Time: 11:18
 *
 * @author Tian.Dong
 */
public class GetOrgVersionRequest extends BaseTokenRequest {
  /**
   * 公司enter_id
   */
  private String enterId;

  public String getEnterId() {
    return enterId;
  }

  public void setEnterId(String enterId) {
    this.enterId = enterId;
  }
}
