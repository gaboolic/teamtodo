package tk.gbl.pojo.request;

import tk.gbl.anno.ValidField;

import java.util.Date;

/**
 * Date: 2015/4/1
 * Time: 14:32
 *
 * @author Tian.Dong
 */
public class AddTaskRequest extends BaseRequest {

  /**
   * 级别
   * 紧急 重要
   * 00 不紧急不重要
   * 01 不紧急重要
   * 10 紧急不重要
   * 11 紧急重要
   */
  Integer level;

  /**
   * 类型
   * 收纳箱
   * 日程
   * 看板
   */
  @ValidField
  Integer type;

  /**
   * 所属日期
   */
  Date date;

  Date startDate;

  Date endDate;

  /**
   * 所属用户
   */
  Integer userId;

  /**
   * 负责人
   */
  Integer owner;


  @ValidField
  String title;


  @ValidField
  String content;

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getOwner() {
    return owner;
  }

  public void setOwner(Integer owner) {
    this.owner = owner;
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
}
