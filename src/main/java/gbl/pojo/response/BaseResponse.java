package gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.util.JsonUtil;

/**
 * 所有给app返回对象的基类
 * <p/>
 * Date: 2014/8/6
 * Time: 14:23
 *
 * @author Tian.Dong
 */
public class BaseResponse {
  /**
   * 返回码
   */
  private String code;
  /**
   * 返回结果
   */
  private String desc;
  /**
   * 错误描述
   */
  private String message = "";

  public BaseResponse() {

  }

  public BaseResponse(ResultType resultType) {
    this.code = resultType.getCode();
    this.desc = resultType.getDesc();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return JsonUtil.toJson(this);
  }
}
