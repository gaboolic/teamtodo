package gbl.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.stereotype.Repository;
import tk.gbl.entity.Topic;
import tk.gbl.util.PagingList;

import java.sql.SQLException;
import java.util.List;

/**
 * Date: 2015/1/20
 * Time: 15:17
 *
 * @author Tian.Dong
 */
@Repository
public class TopicDao extends SuperDao<Topic> {
  public PagingList<Topic> findByPagingDesc(int page, int size) {
    String beanName = getEntityName();
    // 实体类对应的表名
    String tableName = this.getProperty(beanName);
    // 数据库表的主键
    String primary = this.getProperty(tableName + ".id");

    String sql = "select * from %s where isHide is null or isHide = 0 order by %s desc limit %s,%s";
    sql = String.format(sql, tableName, primary, page * size, size);
    PagingList<Topic> pagingList = new PagingList<Topic>();
    List<Topic> list = find(sql);
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
