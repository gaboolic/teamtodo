package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.pojo.request.BaseIdRequest;
import tk.gbl.pojo.request.message.DetailMessageRequest;
import tk.gbl.service.MessageService;

import javax.annotation.Resource;

/**
 * xx艾特了你 跳到同事页面
 * xx回复了你 跳到日志详情页面
 * xx回复了你任务 跳到看板任务、收纳箱页面
 *
 *
 1.黄大给您指派了一条看板任务！

 2.黄三评论了您的日志《5566》！

 3.胡军在李四的日志《5566》中回复了您！

 4.黄二给张三、胡军等人下发了一条日程任务！

 5.郑局评论了您的看板任务《任务必须今天完成》！

 6.王五评论了您的日程任务《任务必须明天完成》！

 7.胡三在他的日志《5566》中@了您！
 *
 * Date: 2015/4/10
 * Time: 18:25
 *
 * @author Tian.Dong
 */
@Controller
@RequestMapping("message")
public class MessageController {

  @Resource
  MessageService messageService;


  @ResponseBody
  @RequestMapping("show")
  public String show() {
    return messageService.show().toString();
  }

  @ResponseBody
  @RequestMapping("ignore")
  public String ignore(BaseIdRequest request){
    return messageService.ignore(request).toString();
  }

  @ResponseBody
  @RequestMapping("self")
  public String self(){
    return messageService.self().toString();
  }


  @ResponseBody
  @RequestMapping("testNotify")
  public String testNotify(){
    messageService.notify();
    return "notify";
  }

  @RequestMapping("detail")
   public String detailPage(DetailMessageRequest request){
    String redirect = messageService.detailPage(request);
    return "redirect:"+redirect;
  }

  @RequestMapping("appDetail")
  public String appDetailPage(DetailMessageRequest request){
    String redirect = messageService.appDetailPage(request);
    return "redirect:"+redirect;
  }


}
