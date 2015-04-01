package tk.gbl.pojo.request;

import tk.gbl.anno.ValidField;

import java.util.Date;

/**
 * Date: 2015/4/1
 * Time: 14:32
 *
 * @author Tian.Dong
 */
public class UpdateTaskRequest extends BaseRequest {

  /**
   * 级别
   * 紧急 重要
   * 00 不紧急不重要
   * 01 不紧急重要
   * 10 紧急不重要
   * 11 紧急重要
   */
  Integer level;

  /**
   * 类型
   * 收纳箱
   * 日程
   * 看板
   */
  @ValidField
  Integer type;

  /**
   * 所属日期
   */
  Date date;

  Date startDate;

  Date endDate;

  /**
   * 所属用户
   */
  Integer userId;

  /**
   * 负责人
   */
  Integer owner;


  @ValidField
  String title;


  @ValidField
  String content;
}
