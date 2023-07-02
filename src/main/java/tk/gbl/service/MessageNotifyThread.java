package tk.gbl.service;

import tk.gbl.agent.request.MessageNotifyRequest;
import tk.gbl.agent.service.SimBaServiceClient;
import tk.gbl.entity.Message;

/**
 * Date: 2015/5/27
 * Time: 11:11
 *
 * @author Tian.Dong
 */
public class MessageNotifyThread implements Runnable {

  Message message;

  public MessageNotifyThread(Message message) {
    this.message = message;
  }

  @Override
  public void run() {
    MessageNotifyRequest request = new MessageNotifyRequest();
    request.setToken(message.getFrom().getToken());
    request.setTitle(message.getTypeName());
    request.setSubject(message.getTypeName());
    request.setContent(message.getFullDesc());
    request.setReceiver(message.getUser().getSsoId());
    request.setUrl(message.getLink());
    SimBaServiceClient.messageNotify(request);
  }
}
