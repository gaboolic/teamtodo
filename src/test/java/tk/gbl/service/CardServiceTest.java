package tk.gbl.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.gbl.CustomMockHttpSession;
import tk.gbl.entity.Team;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.card.AddCardRequest;
import tk.gbl.pojo.request.card.AllCardRequest;
import tk.gbl.pojo.request.card.DeleteCardRequest;
import tk.gbl.pojo.request.card.UpdateCardRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.util.UserInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Date: 2015/4/13
 * Time: 14:20
 *
 * @author Tian.Dong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:springmvc.xml"})
public class CardServiceTest {
  @Resource
  CardService cardService;

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
  public void testAllCard(){
    AllCardRequest request = new AllCardRequest();
    request.setBoardId(1);
    BaseResponse response = cardService.allCard(request);
    System.out.println(response);
  }

  @Test
  public void testAdd(){
    AddCardRequest request = new AddCardRequest();
    request.setBoardId(3);
    request.setName("我的看板"+System.currentTimeMillis());
    BaseResponse response = cardService.addCard(request);
    System.out.println(response);
  }

  @Test
  public void testDelete(){
    DeleteCardRequest request = new DeleteCardRequest();
    request.setId(6);
    BaseResponse response = cardService.delete(request);
    System.out.println(response);
  }

  @Test
  public void testUpdate(){
    UpdateCardRequest request = new UpdateCardRequest();
    request.setId(7);
    request.setName("cardUpdate"+System.currentTimeMillis());
    BaseResponse response = cardService.updateCard(request);
    System.out.println(response);
  }
}
