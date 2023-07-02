package tk.gbl.service;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.SignRequest;
import tk.gbl.service.thread.SyncOrgThread;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 2015/6/10
 * Time: 17:12
 *
 * @author Tian.Dong
 */
@Service
public class OrgService {
  @Resource
  ThreadPoolTaskExecutor taskExecutor;

  Map<String, String> map = new HashMap<String, String>();

  public void syncOrg(TeamCURDService teamCURDService, UserCURDService userCURDService,
                      User user, SignRequest request) {
    SyncOrgThread thread = new SyncOrgThread();
    thread.setUserCURDService(userCURDService);
    thread.setTeamCURDService(teamCURDService);
    thread.setRequest(request);
    thread.setUser(user);
    taskExecutor.execute(thread);
  }
}
