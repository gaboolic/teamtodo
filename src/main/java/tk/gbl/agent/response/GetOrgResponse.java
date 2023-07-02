package tk.gbl.agent.response;

import tk.gbl.agent.response.inner.Company;

/**
 * Date: 2015/4/24
 * Time: 16:06
 *
 * @author Tian.Dong
 */
public class GetOrgResponse extends BaseResponse {
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
    Company company;
    public Company getCompany() {
      return company;
    }

    public void setCompany(Company company) {
      this.company = company;
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
