package tk.gbl.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.gbl.entity.Task;
import tk.gbl.pojo.request.task.DeleteTaskRequest;
import tk.gbl.pojo.request.task.DetailTaskRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.service.TaskService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Date: 2015/4/8
 * Time: 16:50
 *
 * @author Tian.Dong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:springmvc.xml"})
public class TaskDaoTest {

  @Resource
  TaskDao taskDao;

  @Resource
  TaskService taskService;

  @Test
  public void test(){
    List task = taskDao.find("SELECT distinct date FROM Task");
    System.out.println("****"+task);
  }

  @Test
  public void testTaskDetail(){
    DetailTaskRequest request = new DetailTaskRequest();
    request.setId(2);
    BaseResponse response = taskService.detailTask(request);
    System.out.println(response);
  }

  @Test
  public void testDelete(){
    DeleteTaskRequest request = new DeleteTaskRequest();
    request.setId(18);
    taskService.deleteTask(request,null);
  }

  @Test
  public void testGet(){
    Task task = taskDao.get(2);
    System.out.println(task);
  }

}
