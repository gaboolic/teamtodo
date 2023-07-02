package tk.gbl.agent.response;

/**
 * Date: 2015/4/25
 * Time: 11:17
 *
 * @author Tian.Dong
 */
public class GetOrgVersionResponse extends BaseResponse {
  /**
   * <?xml version="1.0" encoding="UTF-8"?>
   * <response type="orgService" subtype=" getOrgXmlVersion " msid="">
   * <result code="0">成功</result>
   * <message>
   * <info xml_version="211"/>
   * </message>
   * </response>
   */

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

  public static class Message {
    Info info;

    public Info getInfo() {
      return info;
    }

    public void setInfo(Info info) {
      this.info = info;
    }

    public static class Info {
      private String xml_version;

      public String getXml_version() {
        return xml_version;
      }

      public void setXml_version(String xml_version) {
        this.xml_version = xml_version;
      }
    }
  }

  public static class Result {
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
