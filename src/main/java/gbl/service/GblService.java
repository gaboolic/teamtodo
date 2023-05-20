package gbl.service;

import org.springframework.stereotype.Service;
import tk.gbl.constants.ResultType;
import tk.gbl.dao.TopicDao;
import tk.gbl.entity.Topic;
import tk.gbl.entity.User;
import tk.gbl.pojo.request.DeleteTopicRequest;
import tk.gbl.pojo.request.ModifyTopicRequest;
import tk.gbl.pojo.request.PublishTopicRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.util.Token;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * Date: 2015/1/30
 * Time: 11:16
 *
 * @author Tian.Dong
 */
@Service
public class GblService {

  @Resource
  TopicDao topicDao;

  public BaseResponse publishTopic(PublishTopicRequest request, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (!user.getUname().equals("gaboolic")) {
      return new BaseResponse(ResultType.NO_AUTH);
    }
    Topic topic = new Topic();
    topic.setTitle(request.getTitle());
    topic.setContent(request.getContent());
    topic.setCreateTime(new Timestamp(Token.getToken()));
    topic.setModifyTime(new Timestamp(Token.getToken()));
    topic.setUid(user.getUid());
    topic.setUname(user.getUname());
    topic.setKeyWord(request.getKeyWord());
    topic.setMostFloor(1);
    topic.setPageView(0);
    topic.setIsHide(0);
    boolean flag = topicDao.save(topic);
    if (flag) {
      return new BaseResponse(ResultType.SUCCESS);
    }
    BaseResponse baseResponse = new BaseResponse(ResultType.ERROR);
    baseResponse.setDesc("发布失败");
    return baseResponse;
  }

  public BaseResponse modifyTopic(ModifyTopicRequest request, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (!user.getUname().equals("gaboolic")) {
      return new BaseResponse(ResultType.NO_AUTH);
    }

    Topic topic = topicDao.get(request.getTid());
    topic.setTitle(request.getTitle());
    topic.setContent(request.getContent());
    topic.setKeyWord(request.getKeyWord());
    topic.setModifyTime(new Timestamp(Token.getToken()));
    boolean flag = topicDao.update(topic);
    if (flag) {
      return new BaseResponse(ResultType.SUCCESS);
    }
    BaseResponse baseResponse = new BaseResponse(ResultType.ERROR);
    baseResponse.setDesc("修改失败");
    return baseResponse;
  }

  public BaseResponse deleteTopic(DeleteTopicRequest request, HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (!user.getUname().equals("gaboolic")) {
      return new BaseResponse(ResultType.NO_AUTH);
    }
    boolean flag = topicDao.delete(request.getTid());
    if (flag) {
      return new BaseResponse(ResultType.SUCCESS);
    }
    BaseResponse baseResponse = new BaseResponse(ResultType.ERROR);
    baseResponse.setDesc("删除失败");
    return baseResponse;
  }


}
