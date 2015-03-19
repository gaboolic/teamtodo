package tk.gbl.entity;

import tk.gbl.util.JsonUtil;

/**
 * Date: 2014/12/21
 * Time: 21:50
 *
 * @author Tian.Dong
 */
public class BaseEntity {
  public String toString(){
    return JsonUtil.toJson(this);
  }
}
