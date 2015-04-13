package tk.gbl.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import tk.gbl.entity.User;
import tk.gbl.entity.log.DailyLog;
import tk.gbl.pojo.request.ShowStarRequest;

import java.util.List;

/**
 * Date: 2015/4/6
 * Time: 10:18
 *
 * @author Tian.Dong
 */
@Repository
public class DailyLogDao extends SuperDao<DailyLog> {
  public List<String> findDistinctDateOfUser(ShowStarRequest request, User user) {
    List<String> dateList = this.findSql("SELECT distinct date FROM DailyLog t where t.user = ?", user);
    return dateList;
  }

  public DailyLog getDetail(String date) {
    Session session = this.getSessionFactory().getCurrentSession();
    String sql = "from DailyLog d where d.date = ?";
    Query query = session.createQuery(sql);
    query.setParameter(0, date);
    return (DailyLog) query.list().get(0);
//    DailyLog dailyLog = this.findFirst("from DailyLog d where d.date = ?",date);
//    return dailyLog;
  }
}
