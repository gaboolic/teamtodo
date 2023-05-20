package gbl.dao;

import org.springframework.stereotype.Repository;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.LoginRequest;
import tk.gbl.pojo.request.RegisterRequest;

import java.util.List;

/**
 * Date: 2014/12/16
 * Time: 13:40
 *
 * @author Tian.Dong
 */
@Repository
public class UserDao extends SuperDao<User> {
  public boolean isConfirm(LoginRequest request) {
    List<?> list = find("select * from user where uname = ? and password = ?", request.getUsername(), request.getPassword());
    if (list == null) {
      return false;
    }
    return list.size() > 0;
  }

  public User getUser(LoginRequest request) {
    List<User> list = find("select * from user where uname = ? and password = ?", request.getUsername(), request.getPassword());
    if (list == null) {
      return null;
    }
    return list.get(0);
  }

  public User getUser(String username) {
    List<User> list = find("select * from user where uname = ?", username);
    if (list == null) {
      return null;
    }
    return list.get(0);
  }

  public boolean isRepeat(RegisterRequest request) {
    List<User> list = find("select * from user where uname =?", request.getUsername());
    if (list == null || list.size() == 0) {
      return false;
    }
    return true;
  }
}
