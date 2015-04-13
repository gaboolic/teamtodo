package tk.gbl.pojo.request.dailylog;

import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.BaseIdRequest;

/**
 * Date: 2015/4/10
 * Time: 17:52
 *
 * @author Tian.Dong
 */
public class ReplyDailyLogRequest extends BaseIdRequest {
  @ValidField
  String content;

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
