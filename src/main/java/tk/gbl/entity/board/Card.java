package tk.gbl.entity.board;

import tk.gbl.entity.BaseEntity;

/**
 * 卡片 (实际上是白板的todo doing cc testing pass每个栏)
 *
 * Date: 2015/3/31
 * Time: 22:11
 *
 * @author Tian.Dong
 */
public class Card extends BaseEntity {
  /**
   * 主键
   */
  Integer id;

  String name;

  Integer teamId;

  Integer userId;
}
