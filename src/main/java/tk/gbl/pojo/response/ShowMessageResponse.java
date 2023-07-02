package tk.gbl.pojo.response;

import tk.gbl.constants.ResultType;
import tk.gbl.pojo.MessagePojo;
import tk.gbl.pojo.UserPojo;

import java.util.List;

/**
 * 通知
 * <p/>
 * Date: 2015/5/1
 * Time: 9:09
 *
 * @author Tian.Dong
 */
public class ShowMessageResponse extends BaseResponse {
  public ShowMessageResponse(ResultType type) {
    super(type);
  }

  List<MessagePojo> messageList;


  UserPojo user;

  public List<MessagePojo> getMessageList() {
    return messageList;
  }

  public void setMessageList(List<MessagePojo> messageList) {
    this.messageList = messageList;
  }

  public UserPojo getUser() {
    return user;
  }

  public void setUser(UserPojo user) {
    this.user = user;
  }
}
