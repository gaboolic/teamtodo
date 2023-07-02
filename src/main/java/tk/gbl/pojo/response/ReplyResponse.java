package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;

/**
 * Date: 2015/4/23
 * Time: 22:54
 *
 * @author Tian.Dong
 */
public class ReplyResponse extends BaseIdResponse {
  private String name;

  private String headImage;


  public ReplyResponse(ResultType type) {
    super(type);
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
