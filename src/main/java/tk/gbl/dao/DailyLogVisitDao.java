package tk.gbl.dao;

import org.springframework.stereotype.Repository;
import tk.gbl.entity.User;
import tk.gbl.entity.log.DailyLog;
import tk.gbl.entity.log.DailyLogVisit;

/**
 * Date: 2015/4/10
 * Time: 16:23
 *
 * @author Tian.Dong
 */
@Repository
public class DailyLogVisitDao extends SuperDao<DailyLogVisit> {
  public DailyLogVisit getByDailyLogAndUser(DailyLog dailyLog, User user) {
    return findFirst("from DailyLogVisit d where d.dailyLog = ? and d.user = ?",dailyLog,user);
  }

  public void saveOrUpdate(DailyLogVisit dailyLogVisit) {
    if(dailyLogVisit.getId()==null) {
      this.save(dailyLogVisit);
    } else {
      this.update(dailyLogVisit);
    }
  }
}
