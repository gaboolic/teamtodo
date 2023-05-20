package gbl.web.controller;//package tk.gbl.web.controller;
//
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
//import iqq.im.QQClient;
//import iqq.im.QQException;
//import iqq.im.QQNotifyListener;
//import iqq.im.WebQQClient;
//import iqq.im.actor.ThreadActorDispatcher;
//import iqq.im.bean.QQStatus;
//import iqq.im.event.QQActionEvent;
//import iqq.im.event.QQActionFuture;
//import iqq.im.event.QQNotifyEvent;
//import iqq.im.event.QQNotifyEventArgs;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
///**
// * Date: 2014/11/13
// * Time: 14:45
// *
// * @author Tian.Dong
// */
//@Controller
//public class TestController {
//
//  public String imageToBase64(BufferedImage bi) throws IOException {
//    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    ImageIO.write(bi, "png", baos);
//    byte[] bytes = baos.toByteArray();
//
//    // BASE64Encoder encoder = new sun.misc.BASE64Encoder();
//    return Base64.encode(bytes);
//  }
//
//  String base64;
//  String code;
//  QQClient client;
//
//  public static void main(String[] args) throws QQException {
//    new TestController().login("1209440675", "nopass_2");
//  }
//
//  @ResponseBody
//  @RequestMapping("login_qq")
//  public String login(String user, String password) throws QQException {
//    if (client != null) {
//      return "";
//    }
//    client = new WebQQClient(user, password, new QQNotifyListener() {
//      public void onNotifyEvent(QQNotifyEvent event) {
//        System.out.println("QQNotifyEvent: " + event.getType() + ", " + event.getTarget());
//        if (event.getType() == QQNotifyEvent.Type.CAPACHA_VERIFY) {
//          try {
//            QQNotifyEventArgs.ImageVerify verify = (QQNotifyEventArgs.ImageVerify) event.getTarget();
//            base64 = imageToBase64(verify.image);
//            while (code == null) {
//              try {
//                Thread.sleep(10000);
//              } catch (InterruptedException e) {
//                e.printStackTrace();
//              }
//            }
//            //String code = new BufferedReader(new InputStreamReader(System.in)).readLine();
//            client.submitVerify(code, event);
//          } catch (IOException e) {
//          }
//        }
//      }
//    }, new ThreadActorDispatcher());
//    //测试同步模式登录
//    QQActionFuture future = client.login(QQStatus.ONLINE, null);
//    QQActionEvent event = future.waitFinalEvent();
//    if (event.getType() == QQActionEvent.Type.EVT_OK) {
//      return "登录成功！！！";
//
//    } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
//      QQException ex = (QQException) event.getTarget();
//      if (ex.getError() == QQException.QQErrorCode.NEED_CAPTCHA) {
//        return "需要验证码";
//      }
//    }
//
//    return "xxx";
//  }
//
//  @ResponseBody
//  @RequestMapping("code")
//  public String setCode(String code) throws QQException {
//    this.code = code;
//    return this.code;
//  }
//
//  @ResponseBody
//  @RequestMapping("base64")
//  public String getBase64() throws QQException {
//    return base64;
//  }
//
//
//}
