package tk.gbl.agent.response.inner;

/**
 * Date: 2015/4/24
 * Time: 16:29
 *
 * @author Tian.Dong
 */
public class Member {
  //name="张三" inner_id="13718962" fax="" birthday="" nbr="66292607" ext="875"
  // sex="1" home_phone="" office_phone="" mobile="" email="zhangsan@163.com"
  // headship="" sort_no="1" login_name="zhangsan

  String name;
  String inner_id;
  String fax;
  String birthday;
  String nbr;
  String ext;
  String sex;
  String home_phone;
  String office_phone;
  String mobile;
  String email;
  String headship;
  String sort_no;
  String login_name;

  String pic_url;
  String face_custom;
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNbr() {
    return nbr;
  }

  public void setNbr(String nbr) {
    this.nbr = nbr;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getInner_id() {
    return inner_id;
  }

  public void setInner_id(String inner_id) {
    this.inner_id = inner_id;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getExt() {
    return ext;
  }

  public void setExt(String ext) {
    this.ext = ext;
  }

  public String getHome_phone() {
    return home_phone;
  }

  public void setHome_phone(String home_phone) {
    this.home_phone = home_phone;
  }

  public String getOffice_phone() {
    return office_phone;
  }

  public void setOffice_phone(String office_phone) {
    this.office_phone = office_phone;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getHeadship() {
    return headship;
  }

  public void setHeadship(String headship) {
    this.headship = headship;
  }

  public String getSort_no() {
    return sort_no;
  }

  public void setSort_no(String sort_no) {
    this.sort_no = sort_no;
  }

  public String getLogin_name() {
    return login_name;
  }

  public void setLogin_name(String login_name) {
    this.login_name = login_name;
  }

  public String getPic_url() {
    return pic_url;
  }

  public void setPic_url(String pic_url) {
    this.pic_url = pic_url;
  }

  public String getFace_custom() {
    return face_custom;
  }

  public void setFace_custom(String face_custom) {
    this.face_custom = face_custom;
  }
}
