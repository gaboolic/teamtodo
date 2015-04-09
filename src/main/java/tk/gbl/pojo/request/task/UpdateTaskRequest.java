package tk.gbl.pojo.request.task;

import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.BaseRequest;

import java.util.Date;

/**
 * Date: 2015/4/1
 * Time: 14:32
 *
 * @author Tian.Dong
 */
public class UpdateTaskRequest extends BaseRequest {
  @ValidField
  Integer id;
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
  Integer type;

  /**
   * 所属日期
   */
  Date date;




  String title;


  String content;

  String status;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
