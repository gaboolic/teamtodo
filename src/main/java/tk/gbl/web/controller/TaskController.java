package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.anno.ValidField;
import tk.gbl.constants.ResultType;
import tk.gbl.pojo.request.task.*;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.service.TaskService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Date: 2015/3/31
 * Time: 21:41
 *
 * @author Tian.Dong
 */
@Controller
@RequestMapping("task")
public class TaskController {

  @Resource
  TaskService taskService;

  @RequestMapping("add")
  @ResponseBody
  public String add(@ValidField AddTaskRequest request) {
    return taskService.addTask(request).toString();
  }

  @RequestMapping("delete")
  @ResponseBody
  public String delete(@ValidField DeleteTaskRequest request) {
    return new BaseResponse(ResultType.SUCCESS).toString();
  }

  @RequestMapping("update")
  @ResponseBody
  public String update(@ValidField UpdateTaskRequest request) {
    return taskService.updateTask(request).toString();
  }

  /**
   * 返回讨论、详情
   * 返回评论和创建人参与人
   * @param request
   * @return
   */
  @RequestMapping("detail")
  @ResponseBody
  public String detail(@ValidField DetailTaskRequest request) {
    return taskService.detailTask(request).toString();
  }

  @RequestMapping("reply")
  @ResponseBody
  public String reply(@ValidField ReplyTaskRequest request,HttpSession session) {
    return taskService.replyTask(request,session).toString();
  }

  /**
   * 修改状态 完成 未完成
   */
  @RequestMapping("changeStatus")
  @ResponseBody
  public String changeStatus(){
    return "";
  }

  /**
   * 修改负责人 参与人
   */
  public String updateOwner(){
    return "";
  }

  /**
   * 返回收纳箱 日程任务
   * @return
   */
  @RequestMapping("show")
  @ResponseBody
  public String show(@ValidField ShowTaskRequest request,HttpSession session) {
    return taskService.showTask(request).toString();
  }

  /**
   * 返回日期
   * @return
   */
  @RequestMapping("showStar")
  @ResponseBody
  public String showStar(@ValidField ShowStarRequest request,HttpSession session) {
    return taskService.showStar(request,session).toString();
  }
}
