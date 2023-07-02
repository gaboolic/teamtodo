package tk.gbl.agent.response.inner;

/**
 * Date: 2015/4/25
 * Time: 9:47
 *
 * @author Tian.Dong
 */
public class SimUser {
  //<user acc_nbr="66292605"
  // user_name="系统管理员" sex="1"
  // manager="2" mobile="" inner_id="13718960"
  // enter_name="日事清测试" enter_id="12674" nick_name="系统管理员"
  // pic_url="http://192.168.1.84:8080/Simba-WS/images/default/1_a.bmp"
  // token="6E727F2279D84398A4EC6D120C1DA347" sid="SIMBA_AUDIOTONE"/></message>
  //</response>

  String acc_nbr;
  String user_name;
  String sex;
  String manager;
  String mobile;
  String inner_id;
  String enter_name;
  String enter_id;
  String nick_name;
  String pic_url;
  String token;
  String sid;

  public String getAcc_nbr() {
    return acc_nbr;
  }

  public void setAcc_nbr(String acc_nbr) {
    this.acc_nbr = acc_nbr;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getManager() {
    return manager;
  }

  public void setManager(String manager) {
    this.manager = manager;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getInner_id() {
    return inner_id;
  }

  public void setInner_id(String inner_id) {
    this.inner_id = inner_id;
  }

  public String getEnter_name() {
    return enter_name;
  }

  public void setEnter_name(String enter_name) {
    this.enter_name = enter_name;
  }

  public String getEnter_id() {
    return enter_id;
  }

  public void setEnter_id(String enter_id) {
    this.enter_id = enter_id;
  }

  public String getNick_name() {
    return nick_name;
  }

  public void setNick_name(String nick_name) {
    this.nick_name = nick_name;
  }

  public String getPic_url() {
    return pic_url;
  }

  public void setPic_url(String pic_url) {
    this.pic_url = pic_url;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getSid() {
    return sid;
  }

  public void setSid(String sid) {
    this.sid = sid;
  }
}
