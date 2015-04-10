package tk.gbl.pojo;

import java.util.Date;

/**
 * Date: 2015/4/10
 * Time: 16:31
 *
 * @author Tian.Dong
 */
public class DailyLogVisitPojo extends BasePojo {

  private String name;

  private String headImage;

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

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
