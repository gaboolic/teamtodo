package tk.gbl.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

  @Test
  public void test(){
    List task = taskDao.find("SELECT distinct date FROM Task");
    System.out.println("****"+task);
  }

}
