package tk.gbl.pojo;

import java.util.Date;

/**
 * Date: 2015/4/10
 * Time: 17:46
 *
 * @author Tian.Dong
 */
public class DailyLogAtPojo {

  private Integer id;



  /**
   * 创建时间
   */
  Date createTime;

  /**
   * 名字
   */
  String name;

  /**
   * 头像
   */
  String headImage;

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
}
