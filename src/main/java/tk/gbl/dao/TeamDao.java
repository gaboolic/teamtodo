package tk.gbl.dao;

import org.springframework.stereotype.Repository;
import tk.gbl.entity.Team;

import java.util.List;

/**
 * Date: 2015/4/13
 * Time: 11:36
 *
 * @author Tian.Dong
 */
@Repository
public class TeamDao extends SuperDao<Team> {
  public Team getBySsoId(String ssoId) {
    return findFirst("from Team t where t.ssoId = ?",ssoId);
  }

  public List<Team> getAllOfDep(Team dep) {
    return find("from Team t where t.father = ?",dep);
  }
}
