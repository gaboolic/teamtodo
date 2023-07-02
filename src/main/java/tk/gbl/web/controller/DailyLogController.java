package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.dailylog.*;
import tk.gbl.pojo.request.ShowStarRequest;
import tk.gbl.service.DailyLogService;

import javax.annotation.Resource;

/**
 * 艾特通知 回复通知
 *
 * Date: 2015/4/1
 * Time: 15:04
 *
 * @author Tian.Dong
 */
@Controller
@RequestMapping("dailyLog")
public class DailyLogController {

  @Resource
  DailyLogService dailyLogService;

  @RequestMapping("list")//
  @ResponseBody
  public String list(@ValidField ListDailyLogRequest request) {
    return dailyLogService.listDailyLog(request).toString();
  }

  @RequestMapping("add")//
  @ResponseBody
  public String add(@ValidField AddDailyLogRequest request) {
    return dailyLogService.addDailyLog(request).toString();
  }

  @RequestMapping("delete")//
  @ResponseBody
  public String delete(@ValidField DeleteDailyLogRequest request) {
    return dailyLogService.deleteDailyLog(request).toString();
  }

  @RequestMapping("update")//
  @ResponseBody
  public String update(@ValidField UpdateDailyLogRequest request) {
    return dailyLogService.updateDailyLog(request).toString();
  }

  /**
   * 详情
   * 返回当天是否有日志
   * 如果有日志返回日志详情
   */
  @RequestMapping("detail") //
  @ResponseBody
  public String detail(@ValidField DetailDailyLogRequest request) {
    return dailyLogService.detailDailyLog(request).toString();
  }

  @RequestMapping("reply") //
  @ResponseBody
  public String reply(@ValidField ReplyDailyLogRequest request) {
    return dailyLogService.replyDailyLog(request).toString();
  }

  /**
   * 返回日期
   * @return
   */
  @RequestMapping("showStar")//
  @ResponseBody
  public String showStar(@ValidField ShowStarRequest request) {
    return dailyLogService.showStar(request).toString();
  }

  @RequestMapping("getAuth")//
  @ResponseBody
  public String getAuth(){
    return dailyLogService.findAuth().toString();
  }

  @RequestMapping("canCreate")//
  @ResponseBody
  public String canCreate(){
    return dailyLogService.canCreate().toString();
  }

  @RequestMapping("changeAuth")//
  @ResponseBody
  public String changeAuth(@ValidField ChangeAuthRequest request) {
    return dailyLogService.changeAuth(request).toString();
  }


}
