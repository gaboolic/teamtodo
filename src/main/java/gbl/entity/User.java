package gbl.entity;

/**
 * Date: 2014/12/16
 * Time: 11:24
 *
 * @author Tian.Dong
 */
public class User {
  private long uid;
  private String uname;
  private String password;
  private String email;

  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }

  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
