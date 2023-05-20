package tk.gbl.agent.response;

/**
 * <response type=" user " subtype="getCustNbrInfo" msid="">
 <result code="0">成功</result>
 <message>
 <user  acc_nbr="66055553" nick_name="用户昵称" head_image ="用户头像URL" person_label=”用户个性签名”/>
 </message>
 </response>
 *
 * Date: 2015/4/16
 * Time: 22:49
 *
 * @author Tian.Dong
 */
public class CustNbrInfoResponse {
  String type;
  String subtype;
  String msid;
  Result result;
  public static class Result{
    String code;

  }
}
