package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.pojo.request.team.GetTeamOrgRequest;
import tk.gbl.pojo.request.team.ShowOtherDailyLogRequest;
import tk.gbl.service.DailyLogService;
import tk.gbl.service.TeamService;

import javax.annotation.Resource;

/**4
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

  @Resource
  DailyLogService dailyLogService;

  /**
   * 不包括自己
   */
  @RequestMapping(value = "myColleagues", method = RequestMethod.GET) //
  @ResponseBody
  public String myColleagues(GetTeamOrgRequest request) {
    return teamService.myColleagues(request).toString();
  }

  /**
   * 不包括自己
   */
  @RequestMapping(value = "myAuthColleagues", method = RequestMethod.GET) //
  @ResponseBody
  public String myAuthColleagues(GetTeamOrgRequest request) {
    return teamService.myAuthColleagues(request).toString();
  }

  /**
   * 包括自己
   */
  @RequestMapping(value = "org", method = RequestMethod.GET) //
  @ResponseBody
  public String org(GetTeamOrgRequest request) {
    return teamService.org(request).toString();
  }


  //公司动态 热门讨论列表
  @RequestMapping(value = "showHotDailyLog", method = RequestMethod.GET) //
  @ResponseBody
  public String showHotDailyLog() {
    return teamService.showHotDiscus().toString();
  }

  //艾特我的日志列表
  @RequestMapping(value = "showAtMeDailyLog", method = RequestMethod.GET) //
  @ResponseBody
  public String showAtMeDailyLog() {
    return teamService.showAtMeDailyLog().toString();
  }

  //人 月 日志列表
  @RequestMapping(value = "showOtherDailyLog", method = RequestMethod.GET) //
  @ResponseBody
  public String showOtherDailyLog(ShowOtherDailyLogRequest request) {
    return teamService.showOtherDailyLog(request).toString();
  }

  //手机端 同事日志列表
  @RequestMapping(value = "showOtherDailyLogList", method = RequestMethod.GET) //
  @ResponseBody
  public String showOtherDailyLogList(ShowOtherDailyLogRequest request) {
    return teamService.showOtherDailyLogList(request).toString();
  }
}
