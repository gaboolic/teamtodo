package tk.gbl.constants;

/**
 * 收纳箱 0
 * 日程   1
 * 看板   2
 * <p/>
 * Date: 2015/6/2
 * Time: 21:07
 *
 * @author Tian.Dong
 */
public enum TaskType {
  ACCEPT(0, "收纳箱"),
  TODO(1, "日程"),
  BOARD(2, "看板");


  TaskType(Integer type, String typeName) {
    this.type = type;
    this.typeName = typeName;
  }

  private Integer type;
  private String typeName;

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public static String getTypeNameByType(int type) {
    switch (type) {
      case 0:
        return ACCEPT.getTypeName();
      case 1:
        return TODO.getTypeName();
      case 2:
        return BOARD.getTypeName();
    }
    return "";
  }
}
