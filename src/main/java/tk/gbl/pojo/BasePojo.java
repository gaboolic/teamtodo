package tk.gbl.pojo;

import tk.gbl.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * pojo的基类，扩充了一个attribute的map
 * <p/>
 * Date: 2014/8/15
 * Time: 11:26
 *
 * @author Tian.Dong
 */
public class BasePojo {
  private Map<String, Object> attribute;

  public void put(String key, Object value) {
    if (attribute == null) {
      synchronized (BasePojo.class) {
        if (attribute == null) {
          attribute = new HashMap<String, Object>();
        }
      }
    }
    attribute.put(key, value);
  }

  public Object get(String key) {
    return attribute.get(key);
  }

  public Map<String, Object> getAttribute() {
    return attribute;
  }

  public void setAttribute(Map<String, Object> attribute) {
    this.attribute = attribute;
  }

  @Override
  public String toString() {
    return JsonUtil.toJson(this);
  }
}
