package gbl.entity;

import java.util.Date;

/**
 * Date: 2014/12/16
 * Time: 11:25
 *
 * @author Tian.Dong
 */
public class Topic {
  private long tid;
  private String title;
  private String content;
  private int mostFloor;
  private long uid;
  private Date createTime;
  private Date modifyTime;
  private int isHide;
  private int pageView;
  private String keyWord;
  private String uname;

  public long getTid() {
    return tid;
  }

  public void setTid(long tid) {
    this.tid = tid;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getMostFloor() {
    return mostFloor;
  }

  public void setMostFloor(int mostFloor) {
    this.mostFloor = mostFloor;
  }

  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public int getIsHide() {
    return isHide;
  }

  public void setIsHide(int isHide) {
    this.isHide = isHide;
  }

  public int getPageView() {
    return pageView;
  }

  public void setPageView(int pageView) {
    this.pageView = pageView;
  }

  public String getKeyWord() {
    return keyWord;
  }

  public void setKeyWord(String keyWord) {
    this.keyWord = keyWord;
  }

  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }
}
