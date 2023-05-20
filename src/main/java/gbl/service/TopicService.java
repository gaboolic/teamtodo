package gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.ReplyDao;
import tk.gbl.dao.TopicDao;
import tk.gbl.dao.UserDao;
import tk.gbl.entity.Reply;
import tk.gbl.entity.Topic;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.AddReplyRequest;
import tk.gbl.pojo.request.ReplyListRequest;
import tk.gbl.pojo.request.SearchTopicRequest;
import tk.gbl.pojo.response.AddReplyResponse;
import tk.gbl.pojo.response.ReplyListResponse;
import tk.gbl.util.PagingList;
import tk.gbl.util.Token;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

/**
 * Date: 2015/1/20
 * Time: 15:16
 *
 * @author Tian.Dong
 */
@Service
public class TopicService {
  @Resource
  UserDao userDao;

  @Resource
  TopicDao topicDao;

  @Resource
  ReplyDao replyDao;

  public PagingList<Topic> findTopicByPageDesc(int page, int i) {
    return topicDao.findByPagingDesc(page, i);
  }

  public Topic findTopicById(int id) {
    Topic topic = topicDao.get(id);
    topic.setPageView(topic.getPageView()+1);
    topicDao.update(topic);
    return topic;
  }

  public List<Reply> findReplyOfTopic(int topicId) {
    return replyDao.find("select * from reply where tid = ?", topicId);
  }

  public Reply findReplyById(int id) {
    return replyDao.get(id);
  }

  public List<Topic> searchTopic(SearchTopicRequest request) {
    return topicDao.find("select * from topic where title like '%?%' or keyWord like '%?%' or content like '%?%'",request.getKey(),request.getKey(),request.getKey());
  }

  public List<Reply> findTopicReplyByTid(ReplyListRequest request) {
    return replyDao.find("select * from reply where tid = ?",request.getTid());
  }

  public ReplyListResponse replyList(ReplyListRequest request) {
    List<Reply> replyList = findTopicReplyByTid(request);
    if(replyList != null) {
      ReplyListResponse replyListResponse = new ReplyListResponse(ResultType.SUCCESS);
      replyListResponse.setReplyList(replyList);
      return replyListResponse;
    }
    ReplyListResponse replyListResponse = new ReplyListResponse(ResultType.NO_DATA);
    return replyListResponse;
  }

  public AddReplyResponse addReply(AddReplyRequest request,HttpSession session) {
    User user = (User) session.getAttribute("user");
    if(user == null) {
      return new AddReplyResponse(ResultType.NOT_LOGIN);
    }
    Topic thisTopic = topicDao.get(request.getTid());

    Reply reply = new Reply();
    reply.setTid(request.getTid());
    reply.setContent(request.getContent());
    reply.setUname(user.getUname());
    reply.setUid(user.getUid());
    reply.setCreateTime(new Timestamp(Token.getToken()));
    reply.setFloor(thisTopic.getMostFloor());
    reply.setIsHide(0);
    replyDao.save(reply);

    thisTopic.setMostFloor(thisTopic.getMostFloor()+1);
    topicDao.update(thisTopic);

    AddReplyResponse response = new AddReplyResponse(ResultType.SUCCESS);
    String content = "%s楼\n" +
        "          %s:\n" +
        "          %s\n" +
        "          <p>%s";
    content = String.format(content,reply.getFloor(),reply.getUname(),reply.getContent().replace("<","&lt").replace(">","&gt"),reply.getCreateTime());
    response.setContent(content);
    return response;
  }
}
