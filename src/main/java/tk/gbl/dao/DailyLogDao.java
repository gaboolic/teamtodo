package tk.gbl.dao;

import org.springframework.stereotype.Repository;
import tk.gbl.entity.User;
import tk.gbl.entity.log.DailyLog;
import tk.gbl.pojo.request.task.ShowStarRequest;

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
    List<String> dateList = this.findSql("SELECT distinct date FROM DailyLog t where t.user = ?",user);
    return dateList;
  }

  public DailyLog getDetail(String date) {
    DailyLog dailyLog = this.findFirst("from DailyLog d where d.date = ?",date);
    return dailyLog;
  }
}
