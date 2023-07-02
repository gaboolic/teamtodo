package tk.gbl.agent.response.inner;

import tk.gbl.anno.ValidList;

import java.util.List;

/**
 * Date: 2015/4/24
 * Time: 16:29
 *
 * @author Tian.Dong
 */
public class Department {
// id="237849" name="测试一部门" clan_order="1

  String id;
  String name;
  String clan_order;

  String synopsis;
  String website;

  @ValidList("member")
  List<Member> members;

  @ValidList("dep")
  List<Department> departments;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getClan_order() {
    return clan_order;
  }

  public void setClan_order(String clan_order) {
    this.clan_order = clan_order;
  }

  public String getSynopsis() {
    return synopsis;
  }

  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public List<Member> getMembers() {
    return members;
  }

  public void setMembers(List<Member> members) {
    this.members = members;
  }

  public List<Department> getDepartments() {
    return departments;
  }

  public void setDepartments(List<Department> departments) {
    this.departments = departments;
  }
}
