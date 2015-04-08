package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.SignRequest;
import tk.gbl.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Date: 2015/4/8
 * Time: 15:47
 *
 * @author Tian.Dong
 */

@Controller
@RequestMapping("out")
public class OutController {
  @Resource
  UserService userService;


  @RequestMapping("sign")
  public String sign(@ValidField SignRequest request,HttpSession session) {
    userService.sign(request, session);
    return "redirect:/index.html";
  }
}
