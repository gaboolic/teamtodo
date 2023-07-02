package tk.gbl.agent.request;

/**
 * Date: 2015/4/23
 * Time: 8:45
 *
 * @author Tian.Dong
 */
public class MessageNotifyRequest extends BaseTokenRequest {
  /**
   * 0 异步
   * 1 同步
   */
  private Integer sync = 0;

  private String title;
  private String content;
  private String subject;
  private String receiver;
  private String url;

  public Integer getSync() {
    return sync;
  }

  public void setSync(Integer sync) {
    this.sync = sync;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
