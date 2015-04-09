package tk.gbl.pojo;

/**
 * Date: 2015/4/2
 * Time: 17:27
 *
 * @author Tian.Dong
 */
public class UserPojo extends BasePojo {
  private Integer id;

  private String name;

  private String headImage;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
