package gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.dao.MessageDao;
import tk.gbl.entity.Message;
import tk.gbl.pojo.request.AddMessageRequest;
import tk.gbl.pojo.request.DeleteMessageRequest;
import tk.gbl.pojo.response.AddMessageResponse;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.service.CommonService;
import tk.gbl.util.PagingList;
import tk.gbl.util.PagingUtil;
import tk.gbl.util.warp.FastJsonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 留言板
 * <p/>
 * Date: 2014/12/20
 * Time: 22:03
 *
 * @author Tian.Dong
 */
@RequestMapping("message")
@Controller
public class MessageController {

  @Resource
  MessageDao messageDao;

  @Resource
  CommonService commonService;

  @RequestMapping("/")
  public String messageList() {
    return "redirect:/message/0";
  }

  @RequestMapping("{page}")
  public String list(@PathVariable int page, Model model) {
    PagingList<Message> list = messageDao.findByPagingDesc(page, 100);
    model.addAttribute("list", list);
    model.addAttribute("paging", PagingUtil.gen(page, list.getTotal()));
    return "message/index";
  }

  @ResponseBody
  @RequestMapping("json/{page}")
  public String jsonList(@PathVariable int page, Model model) {
    PagingList<Message> list = messageDao.findByPagingDesc(page, 10);
    return FastJsonUtil.toJson(list);
  }

  @ResponseBody
  @RequestMapping(value = "add", method = RequestMethod.POST)
  public String add(AddMessageRequest request, HttpSession session) {
    AddMessageResponse baseResponse = commonService.addMessage(request, session);
    return baseResponse.toString();
  }

  @ResponseBody
  @RequestMapping(value = "delete", method = RequestMethod.POST)
  public String delete(DeleteMessageRequest request, HttpSession session) {
    BaseResponse baseResponse = commonService.deleteMessage(request, session);
    return baseResponse.toString();
  }
}
