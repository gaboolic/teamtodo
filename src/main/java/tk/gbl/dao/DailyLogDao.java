package tk.gbl.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import tk.gbl.entity.Team;
import tk.gbl.entity.User;
import tk.gbl.entity.log.DailyLog;
import tk.gbl.pojo.request.ShowStarRequest;
import tk.gbl.pojo.request.dailylog.ListDailyLogRequest;
import tk.gbl.pojo.request.team.ShowOtherDailyLogRequest;

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
    List<String> dateList = this.findSql("SELECT distinct date FROM DailyLog t where t.user = ?" +
        " and t.date like ?", user, request.getYearMonth() + "%");
    return dateList;
  }

  public DailyLog getDetail(Integer id) {
    Session session = this.getSessionFactory().getCurrentSession();
    String sql = "from DailyLog d where d.id = ?";
    Query query = session.createQuery(sql);
    query.setParameter(0, id);
    if (query.list() != null && query.list().size() > 0) {
      return (DailyLog) query.list().get(0);
    }
    return null;
  }

  public DailyLog getDetail(String date, User user) {
    Session session = this.getSessionFactory().getCurrentSession();
    String sql = "from DailyLog d where d.date = ? and d.user = ?";
    Query query = session.createQuery(sql);
    query.setParameter(0, date);
    query.setParameter(1, user);
    if (query.list() != null && query.list().size() > 0) {
      return (DailyLog) query.list().get(0);
    }
    return null;
//    DailyLog dailyLog = this.findFirst("from DailyLog d where d.date = ?",date);
//    return dailyLog;
  }

  public List<DailyLog> showOtherDailyLog(ShowOtherDailyLogRequest request) {
    String sql = "from DailyLog d where d.user.id = ? and d.date like ? order by date asc";
    return find(sql, request.getUserId(), request.getYearMonth() + "%");
  }

  public List<DailyLog> showHotDiscusOfTeam(User user) {
    Session session = this.getSessionFactory().getCurrentSession();
    String sql = "from DailyLog dl where dl.team = ? order by date desc";
    Query query = session.createQuery(sql);
    query.setParameter(0, user.getTeam());
//    query.setParameter(1, user);
    query.setFirstResult(0).setMaxResults(30);
    return query.list();
  }

  public List<DailyLog> showAtMeDailyLog(User user) {
    String sql = "from DailyLog dl where dl.id in " +
        " (select dla.dailyLog.id from DailyLogAt dla where dla.user = ? ) " +
        " order by date desc ";
    return find(sql, user);
  }

  public List<DailyLog> list(User user, Integer page, Integer size) {
    String sql = "from DailyLog dl where dl.user = ? order by date desc";
    return findByPage(page, size, sql, user);
  }


}
