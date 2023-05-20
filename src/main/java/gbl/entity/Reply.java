package gbl.entity;

import java.util.Date;

/**
 * Date: 2014/12/16
 * Time: 11:25
 *
 * @author Tian.Dong
 */
public class Reply {
  private long rid;
  private long tid;
  private int floor;
  private String content;
  private long uid;
  private Date createTime;
  private int isHide;
  private String uname;

  public long getRid() {
    return rid;
  }

  public void setRid(long rid) {
    this.rid = rid;
  }

  public long getTid() {
    return tid;
  }

  public void setTid(long tid) {
    this.tid = tid;
  }

  public int getFloor() {
    return floor;
  }

  public void setFloor(int floor) {
    this.floor = floor;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
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

  public int getIsHide() {
    return isHide;
  }

  public void setIsHide(int isHide) {
    this.isHide = isHide;
  }

  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }
}
