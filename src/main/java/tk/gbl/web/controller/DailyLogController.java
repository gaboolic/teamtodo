package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.dailylog.AddDailyLogRequest;
import tk.gbl.pojo.request.dailylog.DeleteDailyLogRequest;
import tk.gbl.pojo.request.dailylog.DetailDailyLogRequest;
import tk.gbl.pojo.request.dailylog.UpdateDailyLogRequest;
import tk.gbl.pojo.request.task.ShowStarRequest;
import tk.gbl.service.DailyLogService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
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

  @RequestMapping("add")
  @ResponseBody
  public String add(@ValidField AddDailyLogRequest request) {
    return dailyLogService.addDailyLog(request).toString();
  }

  @RequestMapping("delete")
  @ResponseBody
  public String delete(@ValidField DeleteDailyLogRequest request) {
    return dailyLogService.deleteDailyLog(request).toString();
  }

  @RequestMapping("update")
  @ResponseBody
  public String update(@ValidField UpdateDailyLogRequest request) {
    return dailyLogService.updateDailyLog(request).toString();
  }

  /**
   * 详情
   * 返回当天是否有日志
   * 如果有日志返回日志详情
   */
  @RequestMapping("detail")
  @ResponseBody
  public String detail(@ValidField DetailDailyLogRequest request) {
    return dailyLogService.detailDailyLog(request).toString();
  }

  /**
   * 返回日期
   * @return
   */
  @RequestMapping("showStar")//
  @ResponseBody
  public String showStar(@ValidField ShowStarRequest request,HttpSession session) {
    return dailyLogService.showStar(request,session).toString();
  }
}
