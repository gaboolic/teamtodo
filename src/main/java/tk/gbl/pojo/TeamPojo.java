package tk.gbl.pojo;

/**
 * 团队/同事
 *
 * Date: 2015/3/31
 * Time: 22:09
 *
 * @author Tian.Dong
 */
public class TeamPojo extends BasePojo {
  private Integer id;

  private String ssoId;

  /**
   * 类型 0 公司
   */
  private String type;

  private String name;

  private String version;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSsoId() {
    return ssoId;
  }

  public void setSsoId(String ssoId) {
    this.ssoId = ssoId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

}
