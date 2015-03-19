package tk.gbl.util;

import com.alibaba.fastjson.JSON;

/**
 * Date: 2014/5/9
 * Time: 11:16
 *
 * @author Tian.Dong
 */
public class JsonUtil {

  public static String toJson(Object obj) {
    return JSON.toJSONString(obj);
  }

  public static <T> T fromJson(String json, Class<T> clazz) {
    return JSON.parseObject(json, clazz);
  }
}
