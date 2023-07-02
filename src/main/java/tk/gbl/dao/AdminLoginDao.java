package tk.gbl.dao;

import org.springframework.stereotype.Repository;
import tk.gbl.entity.admin.AdminLogin;
import tk.gbl.util.UserInfo;

import java.util.List;

/**
 * Date: 2015/5/20
 * Time: 18:15
 *
 * @author Tian.Dong
 */
@Repository
public class AdminLoginDao extends SuperDao<AdminLogin> {
  public List<AdminLogin> getLast() {
    return findByPage(1, 20, "from AdminLogin a where a.admin = ? or a.username = ? order by id desc", UserInfo.getAdmin(), UserInfo.getAdmin().getUsername());
  }
}
