package gbl.web.filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tk.gbl.dao.UserDao;
import tk.gbl.entity.User;
import tk.gbl.util.MD5Util;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Date: 2015/3/2
 * Time: 16:26
 *
 * @author Tian.Dong
 */
public class LoginInterceptor implements HandlerInterceptor {
  @Resource
  UserDao userDao;

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o) throws Exception {
    Cookie cookie = getCookieByName(httpServletRequest,"up");
    if(cookie==null) {
      return true;
    }
    String up = cookie.getValue();
    if(up == null || up.equals("")) {
      return true;
    }
    String u = up.split("\\|")[0];
    String p = up.split("\\|")[1];
    if(p.equals(MD5Util.md5(u+"gaboolic"))) {
      User user = userDao.get(Integer.parseInt(u));
      httpServletRequest.getSession().setAttribute("user",user);
    }
    return true;
  }

  public static Cookie getCookieByName(HttpServletRequest request,String name){
    Cookie[] cookies = request.getCookies();
    if(cookies == null) {
      return null;
    }
    for(Cookie cookie:cookies) {
      if(cookie.getName().equals(name)) {
        return cookie;
      }
    }
    return null;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, Exception e) throws Exception {

  }
}
