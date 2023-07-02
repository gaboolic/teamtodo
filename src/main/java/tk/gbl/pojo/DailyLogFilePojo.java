package tk.gbl.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class DailyLogFilePojo extends BasePojo {

  private Integer id;


  /**
   * 创建时间
   */
  @JSONField(format = "yyyy-MM-dd")
  Date createTime;

  /**
   * 路径
   */
  String path;

  /**
   * 原始文件名
   */
  String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}