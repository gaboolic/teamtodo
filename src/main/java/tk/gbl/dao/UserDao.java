package tk.gbl.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import tk.gbl.entity.Team;
import tk.gbl.entity.User;
import tk.gbl.entity.log.DailyLog;

import java.util.List;

/**
 * Date: 2015/4/8
 * Time: 15:49
 *
 * @author Tian.Dong
 */
@Repository
public class UserDao extends SuperDao<User> {
  public void updateAuth(User user) {
    User dbUser = this.get(user.getId());
    dbUser.setAuth(user.getAuth());
    this.update(dbUser);
  }

  public List<User> getAllOfTeam(Team team) {
    return find("from User u where u.team = ?", team);
  }

  public List<User> getAllOfDep(Team dep) {
    return find("from User u where u.dep = ?", dep);
  }

  public User getByToken(String sign) {
    return findFirst("from User u where u.token = ?", sign);
  }

  public User getBySsoId(String ssoId) {
    return findFirst("from User u where u.ssoId = ?", ssoId);
  }

  public User getByUsernameAndPassword(String username, String password) {
    return findFirst("from User u where u.username = ? and u.password = ?",username,password);
  }

  public User getDetail(Integer id) {
    Session session = this.getSessionFactory().getCurrentSession();
    String sql = "from User d where d.id = ?";
    Query query = session.createQuery(sql);
    query.setParameter(0, id);
    if (query.list() != null && query.list().size() > 0) {
      return (User) query.list().get(0);
    }
    return null;
  }

  public List<User> getAllOfDepAndHaveAuth(Team dep, User user) {
    return find("from User u where u.dep = ? " +
        "and (u.auth != -1 " +
        "and u in( select ua.user from UserAuth ua where ua.authUser = ?)" +
        "or u.auth = 0" +
        ")", dep,user);
  }
}
