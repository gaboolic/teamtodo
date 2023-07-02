package tk.gbl.pojo.request;

/**
 * Date: 2015/4/25
 * Time: 9:16
 *
 * @author Tian.Dong
 */
public class RedirectRequest extends BaseRequest {
  String sign;
  String redirect;

  public String getRedirect() {
    return redirect;
  }

  public void setRedirect(String redirect) {
    this.redirect = redirect;
  }
}
