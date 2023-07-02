package tk.gbl.web.filter;

import tk.gbl.util.UserInfo;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 此filter是不受spring控制的
 *
 * Date: 2015/4/9
 * Time: 14:03
 *
 * @author Tian.Dong
 */
public class LoginFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest)request;
    UserInfo.setSession(req.getSession());
    UserInfo.setIp(getRemoteIP(req));
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }

  public String getRemoteIP(HttpServletRequest request) {
    if (request.getHeader("x-forwarded-for") == null) {
      return request.getRemoteAddr();
    }
    return request.getHeader("x-forwarded-for");
  }
}
