package tk.gbl.pojo.request.message;

import tk.gbl.pojo.request.BaseRequest;

/**
 * Date: 2015/5/27
 * Time: 11:50
 *
 * @author Tian.Dong
 */
public class DetailMessageRequest extends BaseRequest {
  String uuid;

  Integer isApp;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Integer getIsApp() {
    return isApp;
  }

  public void setIsApp(Integer isApp) {
    this.isApp = isApp;
  }
}
