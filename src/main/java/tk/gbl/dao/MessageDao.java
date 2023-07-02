package tk.gbl.dao;

import org.springframework.stereotype.Repository;
import tk.gbl.entity.Message;
import tk.gbl.entity.User;

import java.util.List;

/**
 * Date: 2015/5/1
 * Time: 9:06
 *
 * @author Tian.Dong
 */
@Repository
public class MessageDao extends SuperDao<Message> {
  public List<Message> showRead(User user) {
    return find("from Message m where m.user = ? and m.status = 1",user);
  }

  public List<Message> show(User user) {
    return find("from Message m where m.user = ? and m.status = 0 order by id desc",user);
  }

  public Message getByUUID(String uuid) {
    return findFirst("from Message m where m.uuid = ?",uuid);
  }
}
