package tk.gbl.entity;

/**
 * Date: 2015/4/1
 * Time: 11:31
 *
 * @author Tian.Dong
 */
public class TaskJoin extends BaseEntity {
  Integer id;

  /**
   * 任务
   */
  Integer taskId;

  /**
   * 参与人
   */
  Integer joinUserId;
}
