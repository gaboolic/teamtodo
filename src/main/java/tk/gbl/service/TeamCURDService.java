package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.dao.TeamDao;
import tk.gbl.entity.Team;

import javax.annotation.Resource;

/**
 * Date: 2015/6/3
 * Time: 15:24
 *
 * @author Tian.Dong
 */
@Service
public class TeamCURDService {

  @Resource
  TeamDao teamDao;

  public Team findTeam(Integer id) {
    return teamDao.get(id);
  }

  public Team findBySsoId(String id) {
    return teamDao.getBySsoId(id);
  }

  public void save(Team company) {
    teamDao.save(company);
  }

  public void saveOrUpdate(Team team) {
    teamDao.saveOrUpdate(team);
  }

  public void update(Team company) {
    teamDao.update(company);
  }
}
