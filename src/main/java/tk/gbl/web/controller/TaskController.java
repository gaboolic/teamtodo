package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.anno.ValidField;
import tk.gbl.constants.ResultType;
import tk.gbl.pojo.request.task.AddTaskRequest;
import tk.gbl.pojo.request.task.DeleteTaskRequest;
import tk.gbl.pojo.request.task.ShowTaskRequest;
import tk.gbl.pojo.request.task.UpdateTaskRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.service.TaskService;

import javax.annotation.Resource;

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

  @RequestMapping("show")
  @ResponseBody
  public String show(@ValidField ShowTaskRequest request) {
    return taskService.showTask(request).toString();
  }
}
