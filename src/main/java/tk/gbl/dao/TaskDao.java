package tk.gbl.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import tk.gbl.entity.Task;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.task.ShowStarRequest;

import java.util.List;

/**
 * Date: 2015/4/1
 * Time: 17:57
 *
 * @author Tian.Dong
 */
@Repository
public class TaskDao extends SuperDao<Task> {
  public List<String> findDistinctDateOfUser(ShowStarRequest request, User user) {
    List<String> dateList = this.findSql("SELECT distinct date FROM Task t where t.user = ?",user);
    return dateList;
  }

  public Task getDetail(Integer id) {
    Session session = this.getSessionFactory().getCurrentSession();
    Task task = (Task) session.get(Task.class,id);
    Hibernate.initialize(task);
    Hibernate.initialize(task.getUser());
    Hibernate.initialize(task.getTaskJoins());
    return task;
  }
}
