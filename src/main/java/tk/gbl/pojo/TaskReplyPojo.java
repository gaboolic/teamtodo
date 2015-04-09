package tk.gbl.pojo;

import java.util.Date;

/**
 * Date: 2015/4/9
 * Time: 10:56
 *
 * @author Tian.Dong
 */
public class TaskReplyPojo extends BasePojo {


  private String name;

  private String headImage;

  /**
   * 评论的内容
   */
  private String content;

  /**
   * 创建时间
   */
  private Date createTime;



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

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
