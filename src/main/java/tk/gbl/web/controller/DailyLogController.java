package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.anno.ValidField;
import tk.gbl.constants.ResultType;
import tk.gbl.pojo.request.AddDailyLogRequest;
import tk.gbl.pojo.request.DeleteDailyLogRequest;
import tk.gbl.pojo.request.UpdateDailyLogRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.service.DailyLogService;

import javax.annotation.Resource;

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
    return new BaseResponse(ResultType.SUCCESS).toString();
  }

  @RequestMapping("update")
  @ResponseBody
  public String update(@ValidField UpdateDailyLogRequest request) {
    return dailyLogService.updateDailyLog(request).toString();
  }
}
