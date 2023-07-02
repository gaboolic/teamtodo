package tk.gbl.pojo;

import tk.gbl.anno.ValidJump;

/**
 * Date: 2015/4/9
 * Time: 18:18
 *
 * @author Tian.Dong
 */
public class DailyLogPojo extends BasePojo {
  /**
   * 所属日期
   */
  String date;

  @ValidJump
  String month;

  @ValidJump
  Integer day;


  /**
   * 标题
   */
  String title;

  /**
   * 备注 内容
   */
  String content;

  @ValidJump
  String replaceContent;

  /**
   * 访问次数
   */
  Integer viewCount;

  Integer fileCount = 0;

  String atNames;

  String username;
  String headImage;
  String star;

  public String getStar() {
    return star;
  }

  public void setStar(String star) {
    this.star = star;
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

  public Integer getViewCount() {
    return viewCount;
  }

  public void setViewCount(Integer viewCount) {
    this.viewCount = viewCount;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public Integer getDay() {
    return day;
  }

  public void setDay(Integer day) {
    this.day = day;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getHeadImage() {
    return headImage;
  }

  public void setHeadImage(String headImage) {
    this.headImage = headImage;
  }

  public Integer getFileCount() {
    return fileCount;
  }

  public void setFileCount(Integer fileCount) {
    this.fileCount = fileCount;
  }

  public String getAtNames() {
    return atNames;
  }

  public void setAtNames(String atNames) {
    this.atNames = atNames;
  }

  public String getReplaceContent() {
    return replaceContent;
  }

  public void setReplaceContent(String replaceContent) {
    this.replaceContent = replaceContent;
  }
}
