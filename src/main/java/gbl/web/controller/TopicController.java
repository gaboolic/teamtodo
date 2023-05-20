package gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.gbl.anno.ValidField;
import tk.gbl.dao.TopicDao;
import tk.gbl.entity.Reply;
import tk.gbl.entity.Topic;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.DeleteTopicRequest;
import tk.gbl.pojo.request.ModifyTopicRequest;
import tk.gbl.pojo.request.PublishTopicRequest;
import tk.gbl.pojo.request.SearchTopicRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.service.GblService;
import tk.gbl.service.TopicService;
import tk.gbl.util.PagingList;
import tk.gbl.util.PagingUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 文章
 * <p/>
 * Date: 2014/12/20
 * Time: 22:07
 *
 * @author Tian.Dong
 */
@RequestMapping("topic")
@Controller
public class TopicController {

  @Resource
  TopicService topicService;

  @Resource
  TopicDao topicDao;

  @Resource
  GblService gblService;

  /**
   * 主题列表
   *
   * @return
   */
  @RequestMapping("/")
  public String topicList() {
    return "redirect:/topic/0";
  }

  /**
   * 主题列表
   *
   * @return
   */
  @RequestMapping(value = "{page}", method = RequestMethod.GET)
  public String list(@PathVariable int page, Model model) {
    PagingList<Topic> list = topicService.findTopicByPageDesc(page, 20);
    model.addAttribute("list", list);
    model.addAttribute("paging", PagingUtil.gen(page, list.getTotal()));
    return "topic/index";
  }

  /**
   * 搜索 主题列表
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "search")
  public String search(@ValidField SearchTopicRequest request, Errors errors, Model model) {
    List<Topic> list = topicService.searchTopic(request);
    model.addAttribute("list", list);
    return "topic/index";
  }

  /**
   * 主题详情
   *
   * @param id
   * @param model
   * @return
   */
  @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
  public String detail(@PathVariable int id, Model model) {
    Topic topic = topicService.findTopicById(id);
    model.addAttribute("topic", topic);
    List<Reply> replyList = topicService.findReplyOfTopic(id);
    model.addAttribute("replyList", replyList);
    return "topic/detail";
  }

  /**
   * 发布主题
   *
   * @param session
   * @return
   */
  @RequestMapping(value = "publish", method = RequestMethod.GET)
  public String publishTopic(HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user != null && user.getUname().equals("gaboolic")) {
      return "/topic/publish";
    }
    return "/index";
  }

  /**
   * 发布主题
   *
   * @param request
   * @param session
   * @return
   */
  @RequestMapping(value = "publish", method = RequestMethod.POST)
  public String publish(PublishTopicRequest request, HttpSession session) {
    BaseResponse response = gblService.publishTopic(request, session);
    if (response.getCode().equals("0")) {
      return "redirect:/topic/0";
    }
    return "error";
  }

  /**
   * 隐藏
   *
   * @return
   */
  @RequestMapping(value = "hide/{id}", method = RequestMethod.GET)
  public String hideTopic(@PathVariable int id, Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user != null && user.getUname().equals("gaboolic")) {
      Topic topic = topicService.findTopicById(id);
      topic.setIsHide(1);
      topicDao.update(topic);
      model.addAttribute("topic",topic);
    }
    return "redirect:/topic/";
  }

  /**
   * 显示
   *
   * @return
   */
  @RequestMapping(value = "show/{id}", method = RequestMethod.GET)
  public String showTopic(@PathVariable int id, Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user != null && user.getUname().equals("gaboolic")) {
      Topic topic = topicService.findTopicById(id);
      topic.setIsHide(0);
      topicDao.update(topic);
    }
    return "redirect:/topic/";
  }

  /**
   * 修改主题页面
   *
   * @return
   */
  @RequestMapping(value = "modify/{id}", method = RequestMethod.GET)
  public String modifyTopic(@PathVariable int id, Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user != null && user.getUname().equals("gaboolic")) {
      Topic topic = topicService.findTopicById(id);
      model.addAttribute("topic",topic);
      return "/topic/modify";
    }
    return "/index";
  }

  /**
   * 修改主题
   *
   * @param request
   * @param session
   * @return
   */
  @RequestMapping(value = "modify", method = RequestMethod.POST)
  public String modify(ModifyTopicRequest request, HttpSession session) {
    gblService.modifyTopic(request, session);
    return "redirect:/topic/detail/"+request.getTid();
  }

  /**
   * 删除主题
   *
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "delete", method = RequestMethod.POST)
  public BaseResponse delete(DeleteTopicRequest request, HttpSession session) {
    return gblService.deleteTopic(request, session);
  }


}
