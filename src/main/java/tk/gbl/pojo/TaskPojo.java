package tk.gbl.pojo;

/**
 * 任务
 *
 * Date: 2015/3/31
 * Time: 22:47
 *
 * @author Tian.Dong
 */
public class TaskPojo extends BasePojo {
  private Integer id;

  /**
   * 级别
   * 紧急 重要
   * 00 不紧急不重要
   * 01 不紧急重要
   * 10 紧急不重要
   * 11 紧急重要
   */
  private Integer level;

  /**
   * 类型
   * 收纳箱 0
   * 日程   1
   * 看板   2
   */
  private Integer type;

  /**
   * 所属日期
   */
  private String date;


  /**
   * 标题
   */
  private String title;

  /**
   * 备注 内容
   */
  private String content;

  private Integer downAccept = 0;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

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

  public Integer getDownAccept() {
    return downAccept;
  }

  public void setDownAccept(Integer downAccept) {
    this.downAccept = downAccept;
  }
}
