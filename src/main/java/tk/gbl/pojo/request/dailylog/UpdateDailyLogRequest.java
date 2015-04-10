package tk.gbl.pojo.request.dailylog;

import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.BaseRequest;

/**
 * Date: 2015/4/6
 * Time: 10:16
 *
 * @author Tian.Dong
 */
public class UpdateDailyLogRequest extends BaseRequest {
  Integer id;
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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
