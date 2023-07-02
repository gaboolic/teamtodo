package tk.gbl.agent.request;

/**
 * Date: 2015/4/23
 * Time: 8:45
 *
 * @author Tian.Dong
 */
public class GetCustNbrInfoRequest extends BaseTokenRequest {
  String accNbr;

  public String getAccNbr() {
    return accNbr;
  }

  public void setAccNbr(String accNbr) {
    this.accNbr = accNbr;
  }
}
