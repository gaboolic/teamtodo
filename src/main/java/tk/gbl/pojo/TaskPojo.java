package tk.gbl.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import tk.gbl.anno.ValidJump;

import java.util.Date;

/**
 * 任务
 * <p/>
 * Date: 2015/3/31
 * Time: 22:47
 *
 * @author Tian.Dong
 */
public class TaskPojo extends BasePojo {
  private Integer id;

  Integer cardId;


  /**
   * 级别
   * 紧急 重要
   * 00 不紧急不重要
   * 01 不紧急重要
   * 10 紧急不重要
   * 11 紧急重要
   */
  private Integer level;

  /**
   * 类型
   * 收纳箱 0
   * 日程   1
   * 看板   2
   */
  private Integer type;

  /**
   * 所属日期
   */
  private String date;

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  /**
   * 标题
   */
  private String title;

  /**
   * 备注 内容
   */
  private String content;

  private Integer downAccept;

  @ValidJump
  private Integer up;

  @ValidJump
  private Integer down;

  String headImage;

  String username;

  String startTime;

  String endTime;

  String status = "0";

  String auth;

  String joinNames;

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

  public Integer getDownAccept() {
    return downAccept;
  }

  public void setDownAccept(Integer downAccept) {
    this.downAccept = downAccept;
  }

  public String getHeadImage() {
    return headImage;
  }

  public void setHeadImage(String headImage) {
    this.headImage = headImage;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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

  public String getJoinNames() {
    return joinNames;
  }

  public void setJoinNames(String joinNames) {
    this.joinNames = joinNames;
  }

  public Integer getUp() {
    return up;
  }

  public void setUp(Integer up) {
    this.up = up;
  }

  public Integer getDown() {
    return down;
  }

  public void setDown(Integer down) {
    this.down = down;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
