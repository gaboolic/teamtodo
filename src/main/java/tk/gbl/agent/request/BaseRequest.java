package tk.gbl.agent.request;

import tk.gbl.util.FmUtil;

/**
 * Date: 2015/4/16
 * Time: 22:40
 *
 * @author Tian.Dong
 */
public class BaseRequest {
  String type;
  String subtype;
  String msid;

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

  public String toString(){
    return FmUtil.getTemplateStr("/core/agent/",this.getClass().getSimpleName()+".ftl",this);
  }
}
