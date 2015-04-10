package tk.gbl.pojo.request.dailylog;

import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.BaseRequest;

/**
 * Date: 2015/4/6
 * Time: 10:16
 *
 * @author Tian.Dong
 */
public class AddDailyLogRequest extends BaseRequest{
  /**
   * 日期
   * 如2015-04-09
   */
  @ValidField
  private String date;

  /**
   * 标题
   */
  @ValidField
  String title;

  /**
   * 备注 内容
   */
  @ValidField
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
