package tk.gbl.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import tk.gbl.entity.Task;
import tk.gbl.entity.User;
import tk.gbl.entity.board.Card;
import tk.gbl.pojo.request.ShowStarRequest;

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
    List<String> dateList = this.findSql("SELECT distinct date FROM Task t where t.user = ? " +
        " and t.type = 1 and t.date like ?", user,request.getYearMonth()+"%");
    return dateList;
  }

  public Task getDetail(Integer id) {
    Session session = this.getSessionFactory().getCurrentSession();
    Task task = (Task) session.get(Task.class, id);
    Hibernate.initialize(task);
    Hibernate.initialize(task.getUser());
    Hibernate.initialize(task.getTaskJoins());
    return task;
  }

  public List<Task> getAllOfCard(Card dbCard) {
    return find("from Task t where t.cardId = ? order by id desc", dbCard.getId());
  }

//  public Task get(int id) {
//    Session session = this.getSessionFactory().getCurrentSession();
//    Task task = (Task) session.get(Task.class, id);
//    System.out.println(task.getUser().getId());
//    return task;
//  }
}
