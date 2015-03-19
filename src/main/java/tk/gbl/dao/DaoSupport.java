package tk.gbl.dao;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DaoSupport<T> {

  protected String getProperty(String key) {
    InputStream inputStream = this.getClass().getClassLoader()
        .getResourceAsStream("beanConfig.properties");
    Properties p = new Properties();
    try {
      p.load(inputStream);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    String value = p.getProperty(key);
    return value;
  }

  protected String getEntityName() {
    Type genType = getClass().getGenericSuperclass();
    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
    Class<?> entityClass = (Class<?>) params[0];
    return entityClass.getSimpleName();
  }

  protected String getEntityName(Object obj) {
    return obj.getClass().getSimpleName();
  }

  protected String getEntityPathName() {
    Type genType = getClass().getGenericSuperclass();
    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
    Class<?> entityClass = (Class<?>) params[0];
    return entityClass.getName();
  }

  protected void setEntity(Object obj, ResultSet rs) throws Exception {
    // Method method = rs.getClass().getMethod("getString",String.class);
    // String s = (String) method.invoke(rs,"username");
    // System.out.println("反射："+s);
    // System.out.println(rs.getString("username"));

    Field[] field = obj.getClass().getDeclaredFields();
    String beanName = getEntityName(obj);
    for (int i = 0; i < field.length; i++) {

      // 属性类型
      Class<?> type = field[i].getType();
      field[i].setAccessible(true);

      Method method = rs.getClass().getMethod("getObject", String.class);
      String tableField = field[i].getName();
      String prokey = beanName + "." + tableField;
      if (this.getProperty(prokey) != null) {
        tableField = this.getProperty(prokey);
      }
      try {
        Object value = method.invoke(rs, tableField);

        value = checkNullObject(value);

        field[i].set(obj, value);
      } catch (Exception e) {
        System.out.println(tableField);
      }
    }

  }

  private Object checkNullObject(Object value) {
    if (value instanceof String) {
      if(value == null) {
        value = "";
      }
      value = ((String) value).trim();
      if (((String) value).length() == 0)
        value = "";

    }
    return value;
  }

  protected void setStmt(Object obj, PreparedStatement stmt) throws Exception {
    // Method method = rs.getClass().getMethod("getString",String.class);
    // String s = (String) method.invoke(rs,"username");
    // System.out.println("反射："+s);
    // System.out.println(rs.getString("username"));

    Method method = stmt.getClass().getMethod("setObject", int.class,
        Object.class);
    Field[] field = obj.getClass().getDeclaredFields();
    String beanName = getEntityName(obj);
    for (int i = 0; i < field.length; i++) {

      // 属性类型
      Class<?> type = field[i].getType();
      field[i].setAccessible(true);

      Object value = field[i].get(obj);
      method.invoke(stmt, i + 1, value);
    }
    // field[0].setAccessible(true);
    // Object value = field[0].get(obj);
    // method.invoke(stmt, field.length ,value);

  }
}
