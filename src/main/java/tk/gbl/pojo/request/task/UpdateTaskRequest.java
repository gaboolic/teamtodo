package tk.gbl.pojo.request.task;

import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.BaseRequest;

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
  String date;


  String title;


  String content;

  String status;

  Integer ownerId;
  String joinIds;
  String joinTeamIds;

  String startTime;
  String endTime;
  private String auth;
  private Integer cardId;

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

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
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

  public Integer getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Integer ownerId) {
    this.ownerId = ownerId;
  }

  public String getJoinIds() {
    return joinIds;
  }

  public void setJoinIds(String joinIds) {
    this.joinIds = joinIds;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getJoinTeamIds() {
    return joinTeamIds;
  }

  public void setJoinTeamIds(String joinTeamIds) {
    this.joinTeamIds = joinTeamIds;
  }

  public String getAuth() {
    return auth;
  }

  public void setAuth(String auth) {
    this.auth = auth;
  }

  public Integer getCardId() {
    return cardId;
  }

  public void setCardId(Integer cardId) {
    this.cardId = cardId;
  }
}
