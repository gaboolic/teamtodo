package tk.gbl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 2015/4/10
 * Time: 15:43
 *
 * @author Tian.Dong
 */
public class CustomMockHttpSession implements HttpSession {
  private Map<String,Object> attr = new HashMap<String,Object>();

  @Override
  public long getCreationTime() {
    return 0;
  }

  @Override
  public String getId() {
    return null;
  }

  @Override
  public long getLastAccessedTime() {
    return 0;
  }

  @Override
  public ServletContext getServletContext() {
    return null;
  }

  @Override
  public void setMaxInactiveInterval(int interval) {

  }

  @Override
  public int getMaxInactiveInterval() {
    return 0;
  }

  @Override
  public HttpSessionContext getSessionContext() {
    return null;
  }

  @Override
  public Object getAttribute(String name) {
    return attr.get(name);
  }

  @Override
  public Object getValue(String name) {
    return null;
  }

  @Override
  public Enumeration getAttributeNames() {
    return null;
  }

  @Override
  public String[] getValueNames() {
    return new String[0];
  }

  @Override
  public void setAttribute(String name, Object value) {
    attr.put(name,value);
  }

  @Override
  public void putValue(String name, Object value) {

  }

  @Override
  public void removeAttribute(String name) {

  }

  @Override
  public void removeValue(String name) {

  }

  @Override
  public void invalidate() {

  }

  @Override
  public boolean isNew() {
    return false;
  }
}
