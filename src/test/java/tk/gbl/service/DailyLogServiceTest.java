package tk.gbl.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.gbl.CustomMockHttpSession;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.dailylog.*;
import tk.gbl.pojo.request.ShowStarRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.util.UserInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Date: 2015/4/10
 * Time: 15:09
 *
 * @author Tian.Dong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:springmvc.xml"})
public class DailyLogServiceTest {

  @Resource
  DailyLogService dailyLogService;

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
  public void testAdd() {
    AddDailyLogRequest request = new AddDailyLogRequest();
    request.setDate("2015-04-08");
    request.setTitle("我这一天啥都没干");
    request.setContent("真是日了狗了");
    request.setAt("2,3");
    BaseResponse response = dailyLogService.addDailyLog(request);
    System.out.println(response);
  }

  @Test
  public void testDelete(){
    DeleteDailyLogRequest request = new DeleteDailyLogRequest();
    request.setId(3);
    BaseResponse response = dailyLogService.deleteDailyLog(request);
    System.out.println(response);

    request.setId(2);
    response = dailyLogService.deleteDailyLog(request);
    System.out.println(response);
  }

  @Test
  public void testUpdate(){
    UpdateDailyLogRequest request = new UpdateDailyLogRequest();
    request.setId(3);
    request.setContent("修改"+System.currentTimeMillis());
    BaseResponse response = dailyLogService.updateDailyLog(request);
    System.out.println(response);

    request.setId(2);
    request.setContent("修改"+System.currentTimeMillis());
    response = dailyLogService.updateDailyLog(request);
    System.out.println(response);
  }

  @Test
  public void testDetail() {
    DetailDailyLogRequest request = new DetailDailyLogRequest();
    request.setDate("2015-04-09");
    BaseResponse response = dailyLogService.detailDailyLog(request);
    System.out.println(response);
  }

  @Test
  public void testDetail2() {
    DetailDailyLogRequest request = new DetailDailyLogRequest();
    request.setDate("2015-04-22");
    BaseResponse response = dailyLogService.detailDailyLog(request);
    System.out.println(response);
  }

  @Test
  public void testReply() {
    ReplyDailyLogRequest request = new ReplyDailyLogRequest();
    request.setId(5);
    request.setContent("评了个论的");
    BaseResponse response = dailyLogService.replyDailyLog(request);
    System.out.println(response);
  }

  @Test
  public void testShowStar() {
    ShowStarRequest request = new ShowStarRequest();
    BaseResponse response = dailyLogService.showStar(request);
    System.out.println(response);
  }

  @Test
  public void testChangeAuth() {
    ChangeAuthRequest request = new ChangeAuthRequest();
    request.setAuth("-1");
    BaseResponse response = dailyLogService.changeAuth(request);
    System.out.println(response);
  }
}
