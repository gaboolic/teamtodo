package gbl.pojo.response;

import tk.gbl.constants.ResultType;

/**作用：用于接收点击个人中心中点击我的资料后，请求服务器接口/employee/center后返回的对象
 * Date: 2014/8/8
 * Time: 18:20
 *
 * @author Tian.Dong
 */
public class CenterResponse extends BaseResponse {
  public CenterResponse(ResultType resultType) {
    super(resultType);
  }

  /**
   * 用户id
   */
  private Integer userId;

  /**
   * 消息条数
   */
  private Integer messageCount;

  /**
   * 头像
   */
  private String headImage;

  /**
   * 用户名
   */
  private String username;

  /**
   * 昵称
   */
  private String nickname;

  /**
   * 单位名称
   */
  private String companyName;

  /**
   * 技能等级
   */
  private Integer techLevel;

  /**
   * 星级
   */
  private Integer star;

  /**
   * 身份
   */
  private Integer status;

  public Integer getMessageCount() {
    return messageCount;
  }

  public void setMessageCount(Integer messageCount) {
    this.messageCount = messageCount;
  }

  public String getHeadImage() {
    return headImage;
  }

  public void setHeadImage(String headImage) {
    this.headImage = headImage;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public Integer getTechLevel() {
    return techLevel;
  }

  public void setTechLevel(Integer techLevel) {
    this.techLevel = techLevel;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public Integer getStar() {
    return star;
  }

  public void setStar(Integer star) {
    this.star = star;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
