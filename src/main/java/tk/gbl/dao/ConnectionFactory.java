package tk.gbl.dao;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.logicalcobwebs.proxool.ProxoolDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {

  private ConnectionFactory() {
  }

  private static ProxoolDataSource ds = null;

  private static String dburl = "jdbc:mysql://127.0.0.1:3306/gblog";
  private static String dbUserName = "root";
  private static String dbUserPsw = "admin";

  static {
    ds = new ProxoolDataSource();
    // 设置JDBC的Driver类
    ds.setDriver("com.mysql.jdbc.Driver");
    // 设置连接池的最大连接数
    ds.setMaximumConnectionCount(10);
    // 设置连接池的最小连接数
    ds.setMinimumConnectionCount(1);
    ds.setMaximumActiveTime(10000);
    ds.setTestBeforeUse(true);
    ds.setHouseKeepingSleepTime(10000);
    ds.setHouseKeepingTestSql("select now()");


    String json = System.getenv("VCAP_SERVICES");
    if (json != null) {
      try {
        JSONObject jsonobj = new JSONObject(json);

        JSONArray gblog = jsonobj.getJSONArray("mysql-5.1");

        JSONObject credentialsobj = gblog.getJSONObject(0).getJSONObject("credentials");

        String name = credentialsobj.getString("name");
        String hostname = credentialsobj.getString("hostname");
        String user = credentialsobj.getString("user");
        String password = credentialsobj.getString("password");

        dburl = ("jdbc:mysql://" + hostname + ":3306/" + name);
        dbUserName = user;
        dbUserPsw = password;
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    ds.setDriverUrl(dburl);
    ds.setUser(dbUserName);
    ds.setPassword(dbUserPsw);
  }

  public static Connection getConnection() {
    try {
      return ds.getConnection();
    } catch (SQLException e) {
      return null;
    }
  }

  public static DataSource getDataSource() {
    return ds;
  }
  // C3P0 end
}
