package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.dao.UserDao;

import javax.annotation.Resource;

/**
 * Date: 2015/6/18
 * Time: 11:48
 *
 * @author Tian.Dong
 */
@Service
public class UserTeamCURDService {
  @Resource
  UserDao userDao;
}
