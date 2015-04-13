package tk.gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.TeamDao;
import tk.gbl.dao.UserDao;
import tk.gbl.entity.User;
import tk.gbl.pojo.UserPojo;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.pojo.response.MyColleaguesResponse;
import tk.gbl.util.TransUtil;
import tk.gbl.util.UserInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2015/4/13
 * Time: 14:22
 *
 * @author Tian.Dong
 */
@Service
public class TeamService {

  @Resource
  TeamDao teamDao;

  @Resource
  UserDao userDao;

  public BaseResponse myColleagues() {
    User user = UserInfo.getUser();
    List<User> teamUsers = userDao.getAllOfTeam(user.getTeam());

    List<UserPojo> myColleagues = new ArrayList<UserPojo>();
    for (User colleague : teamUsers) {
      if (colleague.getId().equals(user.getId())) {
        continue;
      }
      myColleagues.add(TransUtil.gen(colleague, UserPojo.class));
    }
    MyColleaguesResponse response = new MyColleaguesResponse(ResultType.SUCCESS);
    response.setMyColleagues(myColleagues);
    return response;
  }
}
