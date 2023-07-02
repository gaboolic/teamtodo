package tk.gbl.constants;

/**
 * Date: 2015/6/3
 * Time: 10:54
 *
 * @author Tian.Dong
 */
public enum  MessageSubjectType {
  TASK("1", "任务"),
  DIARY("2", "日志"),
  TEAM("3", "同事");

  MessageSubjectType(String type, String subjectTypeName) {
    this.type = type;
    this.subjectTypeName = subjectTypeName;
  }

  private String type;
  private String subjectTypeName;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSubjectTypeName() {
    return subjectTypeName;
  }

  public void setSubjectTypeName(String subjectTypeName) {
    this.subjectTypeName = subjectTypeName;
  }
}
