package tk.gbl.service;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import tk.gbl.agent.service.SimBaServiceClient;
import tk.gbl.constants.MessageSubjectType;
import tk.gbl.constants.Resp;
import tk.gbl.constants.ResultType;
import tk.gbl.constants.TaskType;
import tk.gbl.dao.*;
import tk.gbl.entity.Message;
import tk.gbl.entity.Task;
import tk.gbl.entity.Team;
import tk.gbl.entity.User;
import tk.gbl.entity.log.DailyLog;
import tk.gbl.pojo.MessagePojo;
import tk.gbl.pojo.UserPojo;
import tk.gbl.pojo.request.BaseIdRequest;
import tk.gbl.pojo.request.message.DetailMessageRequest;
import tk.gbl.pojo.response.BaseResponse;
import tk.gbl.pojo.response.ShowMessageResponse;
import tk.gbl.util.TransUtil;
import tk.gbl.util.UserInfo;
import tk.gbl.util.log.LoggerUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 1.黄大给您指派了一条看板任务！
 * <p/>
 * 2.黄三评论了您的日志《5566》！
 * <p/>
 * 3.胡军在李四的日志《5566》中回复了您！
 * <p/>
 * 4.黄二给张三、胡军等人下发了一条日程任务！
 * <p/>
 * 5.郑局评论了您的看板任务《任务必须今天完成》！
 * <p/>
 * 6.王五评论了您的日程任务《任务必须明天完成》！
 * <p/>
 * 7.胡三在他的日志《5566》中@了您！
 * <p/>
 * 消息service
 * <p/>
 * Date: 2015/4/10
 * Time: 18:13
 *
 * @author Tian.Dong
 */
@Service
public class MessageService {

  @Resource
  ThreadPoolTaskExecutor taskExecutor;

  @Resource
  MessageDao messageDao;

  @Resource
  UserDao userDao;

  @Resource
  TeamDao teamDao;

  @Resource
  TaskDao taskDao;

  @Resource
  DailyLogDao dailyLogDao;

  static String host;

  static {
    Properties prop = new Properties();
    InputStream is = SimBaServiceClient.class.getResourceAsStream("/config.properties");
    try {
      prop.load(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
    host = prop.getProperty("host");
  }


  public void atMessage(User fromUser, User toUser, DailyLog dailyLog) {
    final Message message = new Message();
    message.setFrom(fromUser);
    message.setFromUserName(fromUser.getName());
    message.setUser(toUser);
    message.setToUserName(toUser.getName());
    message.setCreateTime(new Date());
    message.setType(MessageSubjectType.TEAM.getType());
    message.setTypeName("艾特信息");
    message.setSubjectId(dailyLog.getId());
    message.setDesc("艾特了");
    String fullDesc = "%s在他的日志《%s》中@了您！";
    message.setFullDesc(String.format(fullDesc, fromUser.getName(), dailyLog.getTitle()));

    String uuid = UUID.randomUUID().toString();
    message.setUuid(uuid);
    message.setLink(host + "/message/detail.do?uuid=" + uuid);
    message.setPcLink("/message/detail.do?uuid=" + uuid);
    message.setAppLink("/message/appDetail.do?uuid=" + uuid);
    message.setCreateTime(new Date());
    messageDao.save(message);

    taskExecutor.execute(new MessageNotifyThread(message));
  }

  public void replyDiaryMessage(User fromUser, User toUser, DailyLog dailyLog) {
    final Message message = new Message();
    message.setFrom(fromUser);
    message.setFromUserName(fromUser.getName());
    message.setUser(toUser);
    message.setToUserName(toUser.getName());
    message.setCreateTime(new Date());
    message.setType(MessageSubjectType.DIARY.getType());
    message.setTypeName("评论日志");
    message.setDesc("评论");
    message.setSubjectId(dailyLog.getId());
    String fullDesc = "%s评论了您的日志《%s》！";
    message.setFullDesc(String.format(fullDesc, fromUser.getName(), dailyLog.getTitle()));

    String uuid = UUID.randomUUID().toString();
    message.setUuid(uuid);
    message.setLink(host + "/message/detail.do?uuid=" + uuid);
    message.setPcLink("/message/detail.do?uuid=" + uuid);
    message.setAppLink("/message/appDetail.do?uuid=" + uuid);

    message.setCreateTime(new Date());
    messageDao.save(message);

    taskExecutor.execute(new MessageNotifyThread(message));
  }

  public void replyTaskMessage(User fromUser, User toUser, Task task) {
    final Message message = new Message();
    message.setFrom(fromUser);
    message.setFromUserName(fromUser.getName());
    message.setUser(toUser);
    message.setToUserName(toUser.getName());
    message.setCreateTime(new Date());
    message.setType(MessageSubjectType.TASK.getType());
    message.setTypeName("评论任务");
    message.setDesc("评论");
    message.setSubjectId(task.getId());

    String fullDesc = "%s评论了您的%s任务《%s》！";
    message.setFullDesc(String.format(fullDesc, fromUser.getName(), TaskType.getTypeNameByType(task.getType()), task.getTitle()));

    String uuid = UUID.randomUUID().toString();
    message.setUuid(uuid);
    message.setLink(host + "/message/detail.do?uuid=" + uuid);
    message.setPcLink("/message/detail.do?uuid=" + uuid);
    message.setAppLink("/message/appDetail.do?uuid=" + uuid);

    message.setCreateTime(new Date());
    messageDao.save(message);

    taskExecutor.execute(new MessageNotifyThread(message));
  }

  public void downAcceptMessage(User fromUser, User toUser, Task task) {
    final Message message = new Message();
    message.setFrom(fromUser);
    message.setFromUserName(fromUser.getName());
    message.setUser(toUser);
    message.setToUserName(toUser.getName());
    message.setCreateTime(new Date());
    message.setType(MessageSubjectType.TASK.getType());
    message.setTypeName("下发信息");
    message.setSubjectId(task.getId());
    message.setDesc("下发了任务给");
    String fullDesc = "%s给您下发了一条%s任务《%s》！";
    message.setFullDesc(String.format(fullDesc, fromUser.getName(), TaskType.getTypeNameByType(task.getType()), task.getTitle()));
    String uuid = UUID.randomUUID().toString();
    message.setUuid(uuid);
    message.setLink(host + "/message/detail.do?uuid=" + uuid);
    message.setPcLink("/message/detail.do?uuid=" + uuid);
    message.setAppLink("/message/appDetail.do?uuid=" + uuid);

    message.setCreateTime(new Date());
    messageDao.save(message);

    taskExecutor.execute(new MessageNotifyThread(message));
  }


  public void joinMessage(User fromUser, User toUser, Task task) {
    final Message message = new Message();
    message.setFrom(fromUser);
    message.setFromUserName(fromUser.getName());
    message.setUser(toUser);
    message.setToUserName(toUser.getName());
    message.setCreateTime(new Date());
    message.setType(MessageSubjectType.TASK.getType());
    message.setTypeName("参与任务信息");
    message.setSubjectId(task.getId());
    message.setDesc("分配了任务给");

    String fullDesc = "%s给您指派了一条%s任务《%s》！";
    message.setFullDesc(String.format(fullDesc, fromUser.getName(), TaskType.getTypeNameByType(task.getType()), task.getTitle()));


    String uuid = UUID.randomUUID().toString();
    message.setUuid(uuid);
    message.setLink(host + "/message/detail.do?uuid=" + uuid);
    message.setPcLink("/message/detail.do?uuid=" + uuid);
    message.setAppLink("/message/appDetail.do?uuid=" + uuid);

    message.setCreateTime(new Date());
    messageDao.save(message);

    taskExecutor.execute(new MessageNotifyThread(message));
  }

  public BaseResponse show() {
    User user = UserInfo.getUser();
    List<Message> messages = null;
    messages = messageDao.show(user);

    int retry = 1;
    while (retry-- > 0 && (messages == null || messages.size() == 0)) {
      messages = messageDao.show(user);
      try {
        Thread.sleep(10000);
      } catch (Exception e) {
        LoggerUtil.error("", e);
        e.printStackTrace();
      }
    }

    List<MessagePojo> messagePojoList = new ArrayList<MessagePojo>();
    for (Message dbMessage : messages) {
      messagePojoList.add(TransUtil.gen(dbMessage, MessagePojo.class));
    }
    ShowMessageResponse response = new ShowMessageResponse(ResultType.SUCCESS);
    response.setMessageList(messagePojoList);
    response.setUser(TransUtil.gen(user, UserPojo.class));
    return response;
  }

  public BaseResponse self() {
    User u = UserInfo.getUser();
    ShowMessageResponse response = new ShowMessageResponse(ResultType.SUCCESS);
    response.setUser(TransUtil.gen(u, UserPojo.class));
    return response;
  }

  public BaseResponse ignore(BaseIdRequest request) {
    Message message = messageDao.get(request.getId());
    message.setStatus("1");
    messageDao.update(message);
    return Resp.success;
  }


  public String detailPage(DetailMessageRequest request) {
    Message message = messageDao.getByUUID(request.getUuid());
    if (message == null) {
      return "/index.html";
    }
    message.setStatus("1");
    messageDao.update(message);

    User user = userDao.get(message.getUser().getId());
    Team team = teamDao.get(user.getTeam().getId());
    user.setTeam(team);
    UserInfo.getSession().setAttribute("user", user);
    if (message.getType().equals(MessageSubjectType.DIARY.getType())) {
      return "/log/index.html?" + message.getSubjectId();
    } else if (message.getType().equals(MessageSubjectType.TASK.getType())) {
      Task task = taskDao.get(message.getSubjectId());
      switch (task.getType()) {
        case 0:
          return "/todo/index.html?" + message.getSubjectId();
        case 1:
          return "/todo/index.html?" + message.getSubjectId();
        case 2:
          return "/board/index.html?" + message.getSubjectId();
        default:
          return "/todo/index.html";
      }
    } else if (message.getType().equals(MessageSubjectType.TEAM.getType())) {
      return "/team/index.html";
    }
    return "/index.html";
  }

  public String appDetailPage(DetailMessageRequest request) {
    Message message = messageDao.getByUUID(request.getUuid());
    if (message == null) {
      return "/indexApp.html#/";
    }
    message.setStatus("1");
    messageDao.update(message);

    User user = userDao.get(message.getUser().getId());
    Team team = teamDao.get(user.getTeam().getId());
    user.setTeam(team);
    UserInfo.getSession().setAttribute("user", user);
    if (message.getType().equals(MessageSubjectType.DIARY.getType())) {
      DailyLog dailyLog = dailyLogDao.get(message.getSubjectId());
      return "/log/m/index.html#/myDiaryDetail/" + dailyLog.getDate();
    } else if (message.getType().equals(MessageSubjectType.TASK.getType())) {
      return "/todo/m/index.html#/edit/" + message.getSubjectId();

    } else if (message.getType().equals(MessageSubjectType.TEAM.getType())) {
      return "/team/m/index.html#/otherDiaryDetail/" + message.getSubjectId();
    }
    return "/indexApp.html#/";
  }
}
