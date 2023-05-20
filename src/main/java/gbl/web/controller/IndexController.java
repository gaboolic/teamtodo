package gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.LoginRequest;
import tk.gbl.pojo.request.RegisterRequest;
import tk.gbl.service.CommonService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Date: 2014/12/16
 * Time: 11:09
 *
 * @author Tian.Dong
 */
@Controller
public class IndexController {

  @Resource
  CommonService commonService;

  @RequestMapping("/")
  public String home(LoginRequest request, HttpSession session) {
    return "index";
  }

  @RequestMapping("/index")
  public String index(LoginRequest request, HttpSession session) {
    return "index";
  }

  @ResponseBody
  @RequestMapping("/login")
  public String login(@ValidField LoginRequest request, HttpSession session,HttpServletResponse response) {
    return commonService.login(request, session,response).toString();
  }

  @RequestMapping("/logout")
  public String logout(HttpServletResponse response,HttpSession session,HttpServletRequest request) {
    session.removeAttribute("user");
    response.addCookie(new Cookie("up",null));
    String refer = request.getHeader("referer");
    if(refer == null ) {
      return "redirect:index";
    }
    if(refer.startsWith("http://localhost") || refer.startsWith("http://www.dongtian.org.cn")
        || refer.startsWith("http://dongtian.org.cn")) {
      return "redirect:"+refer;
    }
    return "redirect:index";
  }

  @ResponseBody
  @RequestMapping("/register")
  public String register(@ValidField RegisterRequest request, Errors errors, HttpSession session) {
    return commonService.register(request, session).toString();
  }

}
