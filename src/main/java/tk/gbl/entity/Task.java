package tk.gbl.entity;

import java.util.Date;

/**
 * 任务
 *
 * Date: 2015/3/31
 * Time: 22:47
 *
 * @author Tian.Dong
 */
public class Task extends BaseEntity {
  /**
   * 主键
   */
  Integer id;

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

  String title;

  String content;
}
