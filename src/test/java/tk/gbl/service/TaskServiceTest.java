package tk.gbl.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.gbl.CustomMockHttpSession;
import tk.gbl.entity.Team;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.task.AddTaskRequest;
import tk.gbl.pojo.request.task.AssignTaskRequest;
import tk.gbl.pojo.request.task.DetailTaskRequest;
import tk.gbl.pojo.request.task.ShowTaskRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.util.UserInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2015/4/14
 * Time: 15:28
 *
 * @author Tian.Dong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:springmvc.xml"})
public class TaskServiceTest {
  @Resource
  TaskService taskService;

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
  public void testAdd(){
    AddTaskRequest request = new AddTaskRequest();
    request.setType(0);
    request.setLevel(3);
    request.setTitle("title");
    request.setContent("content");
    request.setDate("2015-04-19");

    BaseResponse response = taskService.addTask(request);
    System.out.println(response);
  }

  @Test
  public void testDetail(){
    DetailTaskRequest request = new DetailTaskRequest();
    request.setId(9);

    BaseResponse response = taskService.detailTask(request);
    System.out.println(response);
  }

  @Test
  public void testAssignTask(){
    AssignTaskRequest request = new AssignTaskRequest();
    request.setId(9);
    request.setOwnerId(1);

    List<Integer> joinIds = new ArrayList<Integer>();
    joinIds.add(2);
    joinIds.add(3);
    request.setJoinIds(joinIds);

    BaseResponse response = taskService.assignTask(request);
    System.out.println(response);
  }

  @Test
  public void testShowAcceptTask(){
    ShowTaskRequest request = new ShowTaskRequest();
    request.setType(0);
    BaseResponse response = taskService.showTask(request);
    System.out.println(response);
  }
}
