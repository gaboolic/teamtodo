package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.ShowStarRequest;
import tk.gbl.pojo.request.task.*;
import tk.gbl.service.TaskService;

import javax.annotation.Resource;

/**
 * 修改参与人负责人未完成
 *
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

  @RequestMapping("add") //
  @ResponseBody
  public String add(@ValidField AddTaskRequest request) {
    return taskService.addTask(request).toString();
  }

  @RequestMapping("delete") //
  @ResponseBody
  public String delete(@ValidField DeleteTaskRequest request) {
    return taskService.deleteTask(request).toString();
  }

  @RequestMapping("update") //修改 改状态
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
  @RequestMapping("detail")//
  @ResponseBody
  public String detail(@ValidField DetailTaskRequest request) {
    return taskService.detailTask(request).toString();
  }

  @RequestMapping("reply")//
  @ResponseBody
  public String reply(@ValidField ReplyTaskRequest request) {
    return taskService.replyTask(request).toString();
  }



  /**
   * 修改负责人
   * 参与人
   */
  @RequestMapping("assign")//
  @ResponseBody
  public String assign(@ValidField AssignTaskRequest request){
    return taskService.assignTask(request).toString();
  }

  /**
   * 返回收纳箱 日程任务
   * @return
   */
  @RequestMapping("show")//
  @ResponseBody
  public String show(@ValidField ShowTaskRequest request) {
    return taskService.showTask(request).toString();
  }

  /**
   * 返回日期
   * @return
   */
  @RequestMapping("showStar")//
  @ResponseBody
  public String showStar(@ValidField ShowStarRequest request) {
    return taskService.showStar(request).toString();
  }
}
