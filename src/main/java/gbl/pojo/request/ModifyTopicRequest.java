package gbl.pojo.request;

/**
 * Date: 2015/1/30
 * Time: 13:52
 *
 * @author Tian.Dong
 */
public class ModifyTopicRequest {
  private int tid;
  private String title;
  private String content;
  private String keyWord;

  public int getTid() {
    return tid;
  }

  public void setTid(int tid) {
    this.tid = tid;
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

  public String getKeyWord() {
    return keyWord;
  }

  public void setKeyWord(String keyWord) {
    this.keyWord = keyWord;
  }
}
