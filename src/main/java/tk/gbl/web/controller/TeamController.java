package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.service.TeamService;

import javax.annotation.Resource;

/**
 * Date: 2015/4/1
 * Time: 15:05
 *
 * @author Tian.Dong
 */
@Controller
@RequestMapping("team")
public class TeamController {
  @Resource
  TeamService teamService;

  @RequestMapping(value = "myColleagues", method = RequestMethod.GET) //
  @ResponseBody
  public String myColleagues() {
    return teamService.myColleagues().toString();
  }
}
