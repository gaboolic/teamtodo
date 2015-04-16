package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.pojo.request.team.ShowOtherDailyLogRequest;
import tk.gbl.service.DailyLogService;
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

  @Resource
  DailyLogService dailyLogService;

  @RequestMapping(value = "myColleagues", method = RequestMethod.GET) //
  @ResponseBody
  public String myColleagues() {
    return teamService.myColleagues().toString();
  }

  //公司动态 热门讨论列表
  @RequestMapping(value = "showHotDailyLog", method = RequestMethod.GET)
  @ResponseBody
  public String showHotDailyLog() {
    return teamService.showHotDiscus().toString();
  }

  //艾特我的日志列表
  @RequestMapping(value = "showAtMeDailyLog", method = RequestMethod.GET)
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
}
