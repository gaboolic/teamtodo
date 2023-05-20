package gbl.entity;

import java.util.Date;

/**
 * Date: 2014/12/16
 * Time: 11:26
 *
 * @author Tian.Dong
 */
public class Message extends BaseEntity{
  private long mid;
  private long uid;
  private String uname;
  private String email;
  private String content;
  private Date msgTime;

  public long getMid() {
    return mid;
  }

  public void setMid(long mid) {
    this.mid = mid;
  }

  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }

  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getMsgTime() {
    return msgTime;
  }

  public void setMsgTime(Date msgTime) {
    this.msgTime = msgTime;
  }
}
