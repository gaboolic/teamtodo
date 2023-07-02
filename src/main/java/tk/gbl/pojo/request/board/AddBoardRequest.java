package tk.gbl.pojo.request.board;

import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.BaseRequest;

/**
 * Date: 2015/4/6
 * Time: 10:16
 *
 * @author Tian.Dong
 */
public class AddBoardRequest extends BaseRequest{
  @ValidField
  private String name;


  private String auth;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuth() {
    return auth;
  }

  public void setAuth(String auth) {
    this.auth = auth;
  }
}
