package tk.gbl.constants;

/**
 * 返回码和描述的枚举
 * <p/>
 * Date: 2014/8/8
 * Time: 18:09
 *
 * @author Tian.Dong
 */
public enum ResultType {
  SUCCESS("0", "成功"),
  VALID("-1", "参数验证失败"),
  NO_DATA("-2","没有获取到数据"),
  PASSWORD_ERROR("-3","密码错误"),
  CHECK_CODE_ERROR("-4","验证码错误"),
  NO_USER_PHONE("-5","无此用户"),
  RE_PASSWORD_ERROR("124_1","两次输入密码不一致"),
  OLD_PASSWORD_ERROR("124_2","旧密码错误"),
  LOGIN_FAIL("241_1", "登录失败"),
  NOT_LOGIN("241_2", "未登录"),
  USER_ICED("241_3","用户已冻结"),
  REGISTER_REPEAT("240_1", "用户名重复"),
  REGISTER_FAIL("240_2", "注册失败"),
  NO_POSITION("418", "无此岗位"),
  ADD_REPEAT("419", "重复添加"),
  INVITE_REPEAT("415","重复邀请"),
  APPLY_REPEAT("414","重复申请"),
  FOLLOW_REPEAT("417","重复关注"),
  NOT_NULL("416","不能为空"),
  NO_AUTH("420", "没有权限"),
  EXIST("421", "已存在"),
  ERROR("500", "系统异常"),
  OUT("501", "调用webservice失败")
  ;



  ResultType(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private String code;
  private String desc;

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
}
