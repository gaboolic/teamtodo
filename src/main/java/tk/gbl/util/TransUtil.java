package tk.gbl.util;

import tk.gbl.util.log.LoggerUtil;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 把entity对象数据填充到pojo对象
 * <p/>
 * Date: 2014/8/12
 * Time: 16:38
 *
 * @author Tian.Dong
 */
public class TransUtil {

  /**
   * 把entity对象数据填充到pojo对象
   *
   * @param from entity对象
   * @param to   pojo对象
   * @throws NoSuchFieldException
   * @throws IllegalAccessException
   */
  public static void trans(Object from, Object to) {
    if (from == null) {
      return;
    }
    try {
      for (Field field : to.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        Field fromField = from.getClass().getDeclaredField(field.getName());
        fromField.setAccessible(true);
        // fromFieldValue，相当于from.getFromField();
        Object fromFieldValue = fromField.get(from);
        if (fromField.getType().equals(Integer.class)
            || fromField.getType().equals(Double.class)
            || fromField.getType().equals(String.class)
            || fromField.getType().equals(Date.class)
            || fromField.getType().equals(Set.class)
            || fromField.getType().equals(List.class)
            || fromField.getType().equals(Map.class)
            ) {
          //基本类型 或者String，直接赋值
          field.set(to, fromFieldValue);
        } else {
          //对象，递归调用
          field.set(to, field.getType().newInstance());
          trans(fromFieldValue, field.get(to));
        }
      }
    } catch (Exception e) {
      LoggerUtil.error("TransUtil", e);
    }
  }



  public static <T> T gen(Object from, Class<T> clazz) {
    Object to = null;
    try {
      to = clazz.newInstance();
    } catch (Exception e) {
      LoggerUtil.error("TransUtil", e);
    }
    trans(from, to);
    return (T) to;
  }
}
