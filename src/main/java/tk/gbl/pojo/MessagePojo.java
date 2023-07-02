package tk.gbl.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 消息
 * <p/>
 * Date: 2015/4/1
 * Time: 11:27
 *
 * @author Tian.Dong
 */
public class MessagePojo extends BasePojo {
  private Integer id;


  String fromUserName;

  String toUserName;

  /**
   * 类型
   * task
   * board
   * log
   */
  String type;

  String typeName;

  /**
   * 0 未读
   * 1
   * 2 已读
   */
  String status;

  String desc;

  String fullDesc;

  Integer subjectId;

  String link;
  String appLink;
  String pcLink;

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  Date createTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getFromUserName() {
    return fromUserName;
  }

  public void setFromUserName(String fromUserName) {
    this.fromUserName = fromUserName;
  }

  public String getToUserName() {
    return toUserName;
  }

  public void setToUserName(String toUserName) {
    this.toUserName = toUserName;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getFullDesc() {
    return fullDesc;
  }

  public void setFullDesc(String fullDesc) {
    this.fullDesc = fullDesc;
  }

  public Integer getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(Integer subjectId) {
    this.subjectId = subjectId;
  }

  public String getAppLink() {
    return appLink;
  }

  public void setAppLink(String appLink) {
    this.appLink = appLink;
  }

  public String getPcLink() {
    return pcLink;
  }

  public void setPcLink(String pcLink) {
    this.pcLink = pcLink;
  }
}
