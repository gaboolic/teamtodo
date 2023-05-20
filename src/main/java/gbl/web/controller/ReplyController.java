package gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.anno.ValidField;
import tk.gbl.pojo.request.AddReplyRequest;
import tk.gbl.pojo.request.ReplyListRequest;
import tk.gbl.pojo.response.AddReplyResponse;
import tk.gbl.pojo.response.ReplyListResponse;
import tk.gbl.service.TopicService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Date: 2015/1/25
 * Time: 22:15
 *
 * @author Tian.Dong
 */
@RequestMapping("reply")
@Controller
public class ReplyController {

  @Resource
  TopicService topicService;


  @ResponseBody
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String replyList(@ValidField ReplyListRequest request, Errors errors) {
    ReplyListResponse response = topicService.replyList(request);
    return response.toString();
  }

  @ResponseBody
  @RequestMapping(value = "add", method = RequestMethod.POST)
  public String addReply(@ValidField AddReplyRequest request, Errors errors,HttpSession session) {
    AddReplyResponse response = topicService.addReply(request,session);
    return response.toString();
  }

  @ResponseBody
  @RequestMapping(value = "delete", method = RequestMethod.POST)
  public String delete(@ValidField AddReplyRequest request, Errors errors,HttpSession session) {
    return null;
  }
}
