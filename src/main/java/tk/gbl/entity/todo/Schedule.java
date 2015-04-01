package tk.gbl.entity.todo;

import tk.gbl.entity.BaseEntity;

import java.util.Date;

/**
 * 日程
 *
 * Date: 2015/3/31
 * Time: 21:49
 *
 * @author Tian.Dong
 */
public class Schedule extends BaseEntity {
  /**
   * 主键
   */
  Integer id;

  /**
   * 类型
   * 紧急 重要
   * 00 不紧急不重要
   * 01 不紧急重要
   * 10 紧急不重要
   * 11 紧急重要
   */
  Integer type;

  /**
   * 所属是哪一天
   */
  Date date;

  /**
   * 所属用户
   */
  Integer userId;

  String title;

  String content;
}
