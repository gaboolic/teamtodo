package gbl.pojo.request;

/**
 * Date: 2015/1/30
 * Time: 11:17
 *
 * @author Tian.Dong
 */
public class PublishTopicRequest {
  private String title;
  private String content;
  private String keyWord;

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

  public String getKeyWord() {
    return keyWord;
  }

  public void setKeyWord(String keyWord) {
    this.keyWord = keyWord;
  }
}
