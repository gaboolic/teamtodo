package tk.gbl.pojo;

/**
 * Date: 2015/4/9
 * Time: 18:18
 *
 * @author Tian.Dong
 */
public class DailyLogPojo extends BasePojo {

  /**
   * 所属日期
   */
  String date;



  /**
   * 标题
   */
  String title;

  /**
   * 备注 内容
   */
  String content;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
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
}
