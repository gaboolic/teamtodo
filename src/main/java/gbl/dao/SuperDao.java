package gbl.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import tk.gbl.util.PagingList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2014/12/16
 * Time: 13:40
 *
 * @author Tian.Dong
 */
public abstract class SuperDao<T> extends DaoSupport<T> implements BaseDao<T> {

  @Override
  public T get(int id) {
    Object obj = null;
    // 实体类名
    String beanName = getEntityName();
    // 实体类对应的表名
    String tableName = this.getProperty(beanName);
    // 数据库表的主键
    String primary = this.getProperty(tableName + ".id");

    String sql = "select * from %s where %s = ? ";
    sql = String.format(sql, tableName, primary);
    
    Connection conn = null;
    String dbName = this.getProperty(tableName + ".db");
    if (dbName != null && dbName.equals("derby")) {

    } else {
      conn = ConnectionFactory.getConnection();
    }
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        obj = Class.forName(this.getEntityPathName()).newInstance();
        setEntity(obj, rs);
      }
      rs.close();
      stmt.close();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {

        e.printStackTrace();
      }
    }
    return (T) obj;
  }

  @Override
  public T get(String str) {
    Object obj = null;
    // 实体类名
    String beanName = getEntityName();
    // 实体类对应的表名
    String tableName = this.getProperty(beanName);
    // 数据库表的主键
    String primary = this.getProperty(tableName + ".id");

    String sql = "select * from %s where %s = ? ";
    sql = String.format(sql, tableName, primary);
    
    Connection conn = null;
    String dbName = this.getProperty(tableName + ".db");
    if (dbName != null && dbName.equals("derby")) {

    } else {
      conn = ConnectionFactory.getConnection();
    }
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setObject(1, str);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        obj = Class.forName(this.getEntityPathName()).newInstance();
        setEntity(obj, rs);
      }
      rs.close();
      stmt.close();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {

        e.printStackTrace();
      }
    }
    return (T) obj;
  }

  @Override
  public List<T> getAll() {
    List<T> list = new ArrayList<T>();
    // 实体类名
    String beanName = getEntityName();
    // 实体类对应的表名
    String tableName = this.getProperty(beanName);

    String sql = "select * from %s";
    sql = String.format(sql, tableName);
    

    Connection conn = null;
    String dbName = this.getProperty(tableName + ".db");
    if (dbName != null && dbName.equals("derby")) {

    } else {
      conn = ConnectionFactory.getConnection();
    }
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        Object obj;
        obj = Class.forName(this.getEntityPathName()).newInstance();
        setEntity(obj, rs);
        list.add((T) obj);
      }
      rs.close();
      stmt.close();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {

        e.printStackTrace();
      }
    }
    return list;
  }

  @Override
  public boolean save(T e) {
    Object obj = null;
    try {
      obj = Class.forName(this.getEntityPathName()).newInstance();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    obj = obj.getClass().cast(e);
    Field[] fields = obj.getClass().getDeclaredFields();

    // 实体类名
    String beanName = getEntityName();
    // 实体类对应的表名
    String tableName = this.getProperty(beanName);
    // 数据库表的主键
    String primary = this.getProperty(tableName + ".id");
    tableName = "`" + tableName + "`";
    String sql = String.format("insert into %s ( ", tableName);
    for (int i = 0; i < fields.length; i++) {
      String item;
      if (i == 0) {
        item = "%s";
      } else {
        item = ",%s";
      }
      item = String.format(item, fields[i].getName());
      sql += item;
    }
    String whereSql = String.format(") values(");
    sql += whereSql;
    for (int i = 0; i < fields.length; i++) {
      String item;
      if (i == 0) {
        item = "?";
      } else {
        item = ",?";
      }
      item = String.format(item, fields[i].getName());
      sql += item;
    }
    sql += ")";
    
    Connection conn = null;
    String dbName = this.getProperty(tableName + ".db");
    if (dbName != null && dbName.equals("derby")) {

    } else {
      conn = ConnectionFactory.getConnection();
    }
    try {
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      this.setStmt(obj, stmt);

      int i = stmt.executeUpdate();
      ResultSet results = stmt.getGeneratedKeys();
      int num = -1;
      if (results.next()) {
        num = results.getInt(1);
      }
      Field id = obj.getClass().getDeclaredField(primary);
      id.setAccessible(true);
      if (id.getType().equals(Integer.class) || id.getType().equals(int.class)) {
        id.set(e, num);
      } else if (id.getType().equals(Long.class) || id.getType().equals(long.class)) {
        id.set(e, (long) num);
      }


      stmt.close();
      return i > 0;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    } finally {
      try {
        conn.close();
      } catch (SQLException e1) {

        e1.printStackTrace();
      }
    }
  }

  @Override
  public boolean delete(T e) {

    return false;
  }

  @Override
  public boolean delete(int id) {
    // 实体类名
    String beanName = getEntityName();
    // 实体类对应的表名
    String tableName = this.getProperty(beanName);
    // 数据库表的主键
    String primary = this.getProperty(tableName + ".id");

    String sql = "delete from %s where %s = ?";
    sql = String.format(sql, tableName, primary);
    
    Connection conn = null;
    String dbName = this.getProperty(tableName + ".db");
    if (dbName != null && dbName.equals("derby")) {

    } else {
      conn = ConnectionFactory.getConnection();
    }
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, id);
      int i = stmt.executeUpdate();
      stmt.close();
      return i > 0;
    } catch (SQLException e) {
      return false;
    } finally {
      try {
        conn.close();
      } catch (SQLException e1) {

        e1.printStackTrace();
      }
    }
  }

  @Override
  public boolean delete(String str) {
    // 实体类名
    String beanName = getEntityName();
    // 实体类对应的表名
    String tableName = this.getProperty(beanName);
    // 数据库表的主键
    String primary = this.getProperty(tableName + ".id");

    String sql = "delete from %s where %s = ?";
    sql = String.format(sql, tableName, primary);
    
    Connection conn = null;
    String dbName = this.getProperty(tableName + ".db");
    if (dbName != null && dbName.equals("derby")) {

    } else {
      conn = ConnectionFactory.getConnection();
    }
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, str);
      int i = stmt.executeUpdate();
      stmt.close();
      return i > 0;
    } catch (SQLException e) {
      return false;
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {

        e.printStackTrace();
      }
    }
  }

  @Override
  public boolean deleteAll() {

    // 实体类名
    String beanName = getEntityName();
    // 实体类对应的表名
    String tableName = this.getProperty(beanName);
    // 数据库表的主键
    String primary = this.getProperty(tableName + ".id");

    String sql = "delete from %s";
    sql = String.format(sql, tableName, primary);
    
    Connection conn = null;
    String dbName = this.getProperty(tableName + ".db");
    if (dbName != null && dbName.equals("derby")) {

    } else {
      conn = ConnectionFactory.getConnection();
    }
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      int i = stmt.executeUpdate();
      stmt.close();
      return i > 0;
    } catch (SQLException e) {
      return false;
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {

        e.printStackTrace();
      }
    }

  }

  @Override
  public boolean update(T e) {
    Object obj = null;
    try {
      obj = Class.forName(this.getEntityPathName()).newInstance();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    obj = obj.getClass().cast(e);
    Field[] fields = obj.getClass().getDeclaredFields();

    // 实体类名
    String beanName = getEntityName();
    // 实体类对应的表名
    String tableName = this.getProperty(beanName);
    // 数据库表的主键
    String primary = this.getProperty(tableName + ".id");
    String sql = String.format("update %s set ", tableName);
    for (int i = 0; i < fields.length; i++) {
      String item;
      if (i == 0) {
        item = "%s = ? ";
      } else {
        item = ",%s = ? ";
      }
      item = String.format(item, fields[i].getName());
      sql += item;
    }
    String whereSql = String.format("where %s = ?", primary);
    sql += whereSql;
    //
    Connection conn = null;
    String dbName = this.getProperty(tableName + ".db");
    if (dbName != null && dbName.equals("derby")) {

    } else {
      conn = ConnectionFactory.getConnection();
    }
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);

      this.setStmt(obj, stmt);

      // 设置where
      Method method = stmt.getClass().getMethod("setObject", int.class,
          Object.class);
      fields[0].setAccessible(true);
      Object value = fields[0].get(obj);
      method.invoke(stmt, fields.length + 1, value);

      int i = stmt.executeUpdate();
      stmt.close();
      return i > 0;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    } finally {

      try {
        conn.close();
      } catch (SQLException e1) {

        e1.printStackTrace();
      }

    }
  }

  public T findFirst(String sql, Object... objs) {
    List<T> list = null;
    QueryRunner runner = new QueryRunner(ConnectionFactory.getDataSource());
    ResultSetHandler<List<T>> rsh = new BeanListHandler<T>(getEntityClass());
    try {
      list = runner.query(sql, rsh, objs);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (list == null || list.size() == 0) {
      return null;
    }
    return list.get(0);
  }

  public List<T> find(String sql, Object... objs) {
    List<T> list = null;
    QueryRunner runner = new QueryRunner(ConnectionFactory.getDataSource());
    ResultSetHandler<List<T>> rsh = new BeanListHandler<T>(getEntityClass());
    try {
      list = runner.query(sql, rsh, objs);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public int exec(String sql, Object... objs) {
    List<T> list = null;
    QueryRunner runner = new QueryRunner(ConnectionFactory.getDataSource());
    int count = 0;
    try {
      count = runner.update(sql, objs);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  protected Class<T> getEntityClass() {
    Type genType = getClass().getGenericSuperclass();
    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
    Class<T> entityClass = null;
    if (params[0] instanceof Class) {
      entityClass = (Class<T>) params[0];
    } else if (params[0] instanceof ParameterizedType) {
      entityClass = (Class<T>) ((ParameterizedType) params[0]).getRawType();
    }
    return entityClass;
  }

  public List<T> findByPage(int page, int size) {
    String beanName = getEntityName();
    // 实体类对应的表名
    String tableName = this.getProperty(beanName);
    // 数据库表的主键
    String primary = this.getProperty(tableName + ".id");

    String sql = "select * from %s limit %s,%s";
    sql = String.format(sql, tableName, page * size, size);
    return find(sql);
  }

  public PagingList<T> findByPaging(int page, int size) {
    String beanName = getEntityName();
    // 实体类对应的表名
    String tableName = this.getProperty(beanName);
    // 数据库表的主键
    String primary = this.getProperty(tableName + ".id");

    String sql = "select * from %s limit %s,%s";
    sql = String.format(sql, tableName, page * size, size);
    PagingList<T> pagingList = new PagingList<T>();
    List<T> list = find(sql);
    pagingList.addAll(list);

    String sql2 = "select count(*) from %s";
    sql2 = String.format(sql2, tableName);
    QueryRunner runner = new QueryRunner(ConnectionFactory.getDataSource());
    ResultSetHandler rsh = new ArrayListHandler();
    Long count = null;
    try {
      count = (Long) runner.query(sql2, rsh);
      pagingList.setPage(page);
      pagingList.setSize(size);
      pagingList.setTotal((int) (count/size));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return pagingList;
  }

  public List<T> findByPageDesc(int page, int size) {
    String beanName = getEntityName();
    // 实体类对应的表名
    String tableName = this.getProperty(beanName);
    // 数据库表的主键
    String primary = this.getProperty(tableName + ".id");

    String sql = "select * from %s order by %s desc limit %s,%s";
    sql = String.format(sql, tableName, primary, page * size, size);
    return find(sql);
  }

  public PagingList<T> findByPagingDesc(int page, int size) {
    String beanName = getEntityName();
    // 实体类对应的表名
    String tableName = this.getProperty(beanName);
    // 数据库表的主键
    String primary = this.getProperty(tableName + ".id");

    String sql = "select * from %s order by %s desc limit %s,%s";
    sql = String.format(sql, tableName, primary, page * size, size);
    PagingList<T> pagingList = new PagingList<T>();
    List<T> list = find(sql);
    pagingList.addAll(list);

    String sql2 = "select count(*) from %s";
    sql2 = String.format(sql2, tableName);
    QueryRunner runner = new QueryRunner(ConnectionFactory.getDataSource());
    ResultSetHandler rsh = new ScalarHandler();
    Long count = null;
    try {
      count = (Long) runner.query(sql2, rsh);
      pagingList.setPage(page);
      pagingList.setSize(size);
      pagingList.setTotal((int) (count/size));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return pagingList;
  }
}

