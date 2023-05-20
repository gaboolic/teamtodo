package gbl.util;

import java.util.List;

/**
 * Date: 2015/3/2
 * Time: 15:03
 *
 * @author Tian.Dong
 */
public class Paging {
  /**
   * 前页
   */
  private Integer prev;

  /**
   * 当前页
   */
  private Integer page;

  /**
   * 后页
   */
  private Integer next;

  /**
   * 页大小
   */
  private Integer size;

  /**
   * 总页数
   */
  private Integer total;


  private List<Integer> list;

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

  public List<Integer> getList() {
    return list;
  }

  public void setList(List<Integer> list) {
    this.list = list;
  }

  public Integer getPrev() {
    return prev;
  }

  public void setPrev(Integer prev) {
    this.prev = prev;
  }

  public Integer getNext() {
    return next;
  }

  public void setNext(Integer next) {
    this.next = next;
  }
}
