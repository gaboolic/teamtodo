package tk.gbl.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.gbl.CustomMockHttpSession;
import tk.gbl.entity.Team;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.team.ShowOtherDailyLogRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.util.UserInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Date: 2015/4/13
 * Time: 17:58
 *
 * @author Tian.Dong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:springmvc.xml"})
public class TeamServiceTest {

  @Resource
  TeamService teamService;

  @BeforeClass
  public static void before() {
    HttpSession session = new CustomMockHttpSession();
    User user = new User();
    user.setId(1);
    user.setHeadImage("2323123.jpg");
    user.setName("小黑黑");
    Team team = new Team();
    team.setId(1);
    user.setTeam(team);
    session.setAttribute("user", user);
    UserInfo.setSession(session);
  }

  @Test
  public void testMyColleagues(){
    BaseResponse response = teamService.myColleagues();
    System.out.println(response);
  }

  @Test
  public void testShowHotDiscus(){
    System.out.println(teamService.showHotDiscus());
  }

  @Test
  public void testShowOtherDailyLog(){
    ShowOtherDailyLogRequest request = new ShowOtherDailyLogRequest();
    request.setUserId(1);
    request.setYearMonth("2015-04");
    System.out.println(teamService.showOtherDailyLog(request));
  }

  @Test
  public void testShowAtMeDailyLog(){
    BaseResponse response = teamService.showAtMeDailyLog();
    System.out.println(response);
  }
}
