package tk.gbl.agent.response;

import tk.gbl.agent.response.inner.SimUser;

/**
 * Date: 2015/4/25
 * Time: 9:44
 *
 * @author Tian.Dong
 */
public class TokenLoginResponse extends BaseResponse {
  //<response type="login" subtype="checkedToken" msid="">
 // <result code="0">成功</result>
  //<message><user acc_nbr="66292605"
  // user_name="系统管理员" sex="1" manager="2" mobile="" inner_id="13718960" enter_name="日事清测试" enter_id="12674" nick_name="系统管理员" pic_url="http://192.168.1.84:8080/Simba-WS/images/default/1_a.bmp" token="6E727F2279D84398A4EC6D120C1DA347" sid="SIMBA_AUDIOTONE"/></message>
  //</response>

  Result result;
  Message message;

  public Result getResult() {
    return result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  public static class Message{
    SimUser user;

    public SimUser getUser() {
      return user;
    }

    public void setUser(SimUser user) {
      this.user = user;
    }
  }
  public static class Result{
    String code;
    String data;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }
  }
}
