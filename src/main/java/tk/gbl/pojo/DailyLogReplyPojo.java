package tk.gbl.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Date: 2015/4/10
 * Time: 17:47
 *
 * @author Tian.Dong
 */

public class DailyLogReplyPojo {

  private Integer id;


  /**
   * 创建时间
   */
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  Date createTime;

  /**
   * 名字
   */
  String name;

  /**
   * 头像
   */
  String headImage;

  /**
   * 评论的内容
   */
  String content;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHeadImage() {
    return headImage;
  }

  public void setHeadImage(String headImage) {
    this.headImage = headImage;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
