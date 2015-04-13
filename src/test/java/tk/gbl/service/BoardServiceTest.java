package tk.gbl.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.gbl.CustomMockHttpSession;
import tk.gbl.entity.Team;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.board.AddBoardRequest;
import tk.gbl.pojo.request.board.DeleteBoardRequest;
import tk.gbl.pojo.request.board.UpdateBoardRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.util.UserInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Date: 2015/4/13
 * Time: 10:38
 *
 * @author Tian.Dong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:springmvc.xml"})
public class BoardServiceTest {

  @Resource
  BoardService boardService;

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
  public void testAll() {
    BaseResponse response = boardService.allBoard();
    System.out.println(response);
  }

  @Test
  public void testAdd() {
    AddBoardRequest request = new AddBoardRequest();
    request.setAuth("0");
    request.setName("我的看板");
    BaseResponse response = boardService.addBoard(request);
    System.out.println(response);
  }

  @Test
  public void testDelete() {
    DeleteBoardRequest request = new DeleteBoardRequest();
    request.setId(3);
    BaseResponse response = boardService.delete(request);
    System.out.println(response);
  }

  @Test
  public void testUpdate() {
    UpdateBoardRequest request = new UpdateBoardRequest();
    request.setId(4);
    request.setName("修改" + System.currentTimeMillis());
    BaseResponse response = boardService.updateBoard(request);
    System.out.println(response);
  }
}
