package tk.gbl.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2015/3/2
 * Time: 15:02
 *
 * @author Tian.Dong
 */
public class PagingUtil {
  public static Paging gen(int page, Integer total) {
    Paging paging = new Paging();
    paging.setTotal(total);
    paging.setPrev(max(0, page - 1));
    paging.setPage(page);
    paging.setNext(min(total-1, page + 1));
    if (page == 0 || page ==1) {
      List<Integer> list = new ArrayList<Integer>();
      for (int i = 0; i < min(5, total); i++) {
        list.add(i);
      }
      paging.setList(list);
    } else {
      List<Integer> list = new ArrayList<Integer>();
      for (int i = page - 2; i < min(page + 3, total); i++) {
        list.add(i);
      }
      paging.setList(list);
    }
    return paging;
  }

  private static Integer max(int a, int b) {
    return a > b ? a : b;
  }

  public static int min(int a, int b) {
    return a < b ? a : b;
  }
}
