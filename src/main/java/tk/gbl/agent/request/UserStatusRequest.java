package tk.gbl.agent.request;

/**
 * Date: 2015/4/23
 * Time: 8:46
 *
 * @author Tian.Dong
 */
public class UserStatusRequest extends BaseRequest {
  String accNbr;

  public String getAccNbr() {
    return accNbr;
  }

  public void setAccNbr(String accNbr) {
    this.accNbr = accNbr;
  }
}
