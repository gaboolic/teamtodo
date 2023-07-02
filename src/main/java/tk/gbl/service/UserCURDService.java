package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.dao.UserDao;
import tk.gbl.entity.User;

import javax.annotation.Resource;

/**
 * Date: 2015/6/3
 * Time: 15:24
 *
 * @author Tian.Dong
 */
@Service
public class UserCURDService {

  @Resource
  UserDao userDao;

  public User findByToken(String sign) {
    return userDao.getByToken(sign);
  }

  public User findBySsoId(String accNbr) {
    return userDao.getBySsoId(accNbr);
  }

  public void saveOrUpdate(User user) {
    userDao.saveOrUpdate(user);
  }

  public void save(User user) {
    userDao.save(user);
  }

  public User findUser(Integer id) {
    return userDao.get(id);
  }

  public void update(User user) {
    userDao.update(user);
  }
}
