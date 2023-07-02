package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.BaseIdRequest;
import tk.gbl.pojo.request.SignRequest;
import tk.gbl.pojo.request.admin.DeleteAuthRequest;
import tk.gbl.pojo.request.admin.SetDepManagerRequest;
import tk.gbl.pojo.request.dailylog.ChangeAuthRequest;
import tk.gbl.pojo.request.team.GetTeamOrgRequest;
import tk.gbl.service.AdminService;

import javax.annotation.Resource;

/**
 * Date: 2015/4/8
 * Time: 15:06
 *
 * @author Tian.Dong
 */
@Controller
@RequestMapping("admin")
public class AdminController {

  @Resource
  AdminService adminService;


  @ResponseBody
  @RequestMapping("login")
  public String login(SignRequest request){
    return adminService.login(request).toString();
  }

  @ResponseBody
  @RequestMapping("tokenLogin")
  public String tokenLogin(SignRequest request){
    return adminService.tokenLogin(request).toString();
  }

  @ResponseBody
  @RequestMapping("self")
  public String self(){
    return adminService.self().toString();
  }

  @ResponseBody
  @RequestMapping("loginList")
  public String loginList(){
    return adminService.loginList().toString();
  }

  /**
   * 包括自己
   */
  @RequestMapping(value = "org", method = RequestMethod.GET) //
  @ResponseBody
  public String org(GetTeamOrgRequest request) {
    return adminService.org(request).toString();
  }

  @ResponseBody
  @RequestMapping("authList")
  public String authList(BaseIdRequest request){
    return adminService.authList(request).toString();
  }

  @RequestMapping("deleteAuth")//
  @ResponseBody
  public String deleteAuth(@ValidField DeleteAuthRequest request) {
    return adminService.deleteAuth(request).toString();
  }

  @RequestMapping("changeAuth")//
  @ResponseBody
  public String changeAuth(@ValidField ChangeAuthRequest request) {
    return adminService.changeAuth(request).toString();
  }


  @RequestMapping("setDepManager")//
  @ResponseBody
  public String setDepManager(@ValidField SetDepManagerRequest request) {
    return adminService.setDepManager(request).toString();
  }

  public String image(){
    return "";
  }
}
