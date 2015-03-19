package tk.gbl.util;

import java.util.ArrayList;

/**
 * Date: 2015/3/2
 * Time: 14:05
 *
 * @author Tian.Dong
 */
public class PagingList<T> extends ArrayList<T> {
  /**
   * 当前页
   */
  private Integer page;

  /**
   * 页大小
   */
  private Integer size;

  /**
   * 总页数
   */
  private Integer total;

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }
}
