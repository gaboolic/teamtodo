package tk.gbl.agent.response.inner;

import tk.gbl.anno.ValidList;

import java.util.List;

/**
 * Date: 2015/4/24
 * Time: 16:29
 *
 * @author Tian.Dong
 */
public class Company {
  // id="237848" name="日事清测试" enter_id="12674" ver="4" nbr="66292603" domain="rishiqing" address=""
  // post_no="" short_name="日事清测试"
  // tel_no="" fax_no="" email="rishiqing@163.com" link_man="" tdbs="65537"
  String id;
  String name;
  String enter_id;
  String ver;
  String nbr;
  String domain;
  String address;
  String post_no;
  String short_name;
  String tel_no;
  String fax_no;
  String email;
  String link_man;
  String tdbs;
  String synopsis;
  String website;

  @ValidList("member")
  List<Member> members;

  @ValidList("dep")
  List<Department> departments;

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

  public String getEnter_id() {
    return enter_id;
  }

  public void setEnter_id(String enter_id) {
    this.enter_id = enter_id;
  }

  public String getVer() {
    return ver;
  }

  public void setVer(String ver) {
    this.ver = ver;
  }

  public String getNbr() {
    return nbr;
  }

  public void setNbr(String nbr) {
    this.nbr = nbr;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPost_no() {
    return post_no;
  }

  public void setPost_no(String post_no) {
    this.post_no = post_no;
  }

  public String getShort_name() {
    return short_name;
  }

  public void setShort_name(String short_name) {
    this.short_name = short_name;
  }

  public String getTel_no() {
    return tel_no;
  }

  public void setTel_no(String tel_no) {
    this.tel_no = tel_no;
  }

  public String getFax_no() {
    return fax_no;
  }

  public void setFax_no(String fax_no) {
    this.fax_no = fax_no;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLink_man() {
    return link_man;
  }

  public void setLink_man(String link_man) {
    this.link_man = link_man;
  }

  public String getTdbs() {
    return tdbs;
  }

  public void setTdbs(String tdbs) {
    this.tdbs = tdbs;
  }
}
