package tk.gbl.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.gbl.entity.board.Board;

import javax.annotation.Resource;

/**
 * Date: 2015/4/13
 * Time: 16:44
 *
 * @author Tian.Dong
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:springmvc.xml"})
public class CardDaoTest {

  @Resource
  CardDao cardDao;

  @Test
  public void test(){
    Board board;
    int max = -1;

    board= new Board();
    board.setId(1);
    max = cardDao.getMaxOfBoard(board);
    System.out.println(max);

    board= new Board();
    board.setId(2);
    max = cardDao.getMaxOfBoard(board);
    System.out.println(max);
  }
}
