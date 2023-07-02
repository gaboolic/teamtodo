package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;

/**
 * Date: 2015/4/23
 * Time: 21:18
 *
 * @author Tian.Dong
 */
public class BaseIdResponse extends BaseResponse {
  Integer id;
  private String headImage;
  private String username;

  public BaseIdResponse(ResultType type) {
    super(type);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setHeadImage(String headImage) {
    this.headImage = headImage;
  }

  public String getHeadImage() {
    return headImage;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
