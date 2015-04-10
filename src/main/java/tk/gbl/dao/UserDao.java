package tk.gbl.dao;

import org.springframework.stereotype.Repository;
import tk.gbl.entity.User;

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
}
