package tk.gbl.pojo.request.task;

import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.BaseRequest;

/**
 * Date: 2015/4/1
 * Time: 14:32
 *
 * @author Tian.Dong
 */
public class ShowTaskRequest extends BaseRequest {



  /**
   * 类型
   * 收纳箱 0
   * 日程   1
   * 看板   2
   */
  @ValidField
  Integer type;

  /**
   * 所属日期
   */
  String date;


  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
