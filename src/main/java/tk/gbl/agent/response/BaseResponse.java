package tk.gbl.agent.response;

/**
 * Date: 2015/4/24
 * Time: 16:06
 *
 * @author Tian.Dong
 */
public class BaseResponse {
  String type;
  String subtype;
  String msid;
  String code;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSubtype() {
    return subtype;
  }

  public void setSubtype(String subtype) {
    this.subtype = subtype;
  }

  public String getMsid() {
    return msid;
  }

  public void setMsid(String msid) {
    this.msid = msid;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
