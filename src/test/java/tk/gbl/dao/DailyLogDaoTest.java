package tk.gbl.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.gbl.CustomMockHttpSession;
import tk.gbl.entity.User;
import tk.gbl.entity.log.DailyLog;
import tk.gbl.pojo.request.team.ShowOtherDailyLogRequest;
import tk.gbl.util.UserInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Date: 2015/4/15
 * Time: 11:53
 *
 * @author Tian.Dong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:springmvc.xml"})
public class DailyLogDaoTest {
  @Resource
  DailyLogDao dailyLogDao;

  @BeforeClass
  public static void before(){
    HttpSession session = new CustomMockHttpSession();
    User user = new User();
    user.setId(1);
    user.setHeadImage("2323123.jpg");
    user.setName("小黑黑");
    session.setAttribute("user",user);
    UserInfo.setSession(session);
  }

  @Test
  public void testShowOtherDailyLog(){
    ShowOtherDailyLogRequest request = new ShowOtherDailyLogRequest();
    request.setUserId(2);
    request.setYearMonth("2015-04");
    List<DailyLog> result = dailyLogDao.showOtherDailyLog(request);
  }

  @Test
  public void testShowHotDiscusOfTeam(){

    List<DailyLog> result = dailyLogDao.showHotDiscusOfTeam(UserInfo.getUser());
    System.out.println(result);
  }
}
