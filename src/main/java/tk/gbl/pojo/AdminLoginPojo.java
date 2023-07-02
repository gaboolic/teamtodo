package tk.gbl.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Date: 2015/5/18
 * Time: 13:13
 *
 * @author Tian.Dong
 */
public class AdminLoginPojo extends BasePojo {
  private Integer id;

  String ip;

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  Date createTime;

  /**
   * 1 账号密码
   * 2 im
   */
  String type;

  Integer isSuccess;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getIsSuccess() {
    return isSuccess;
  }

  public void setIsSuccess(Integer isSuccess) {
    this.isSuccess = isSuccess;
  }
}
