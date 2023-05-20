package gbl.service;///*
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
///**
// * Project  : WebQQCoreAsync
// * Package  : iqq.im
// * File     : WebQQClientTest.java
// * Author   : solosky < solosky772@qq.com >
// * Created  : 2012-9-6
// * License  : Apache License 2.0
// */
//package tk.gbl.service;
//
//import iqq.im.QQClient;
//import iqq.im.QQException;
//import iqq.im.QQNotifyListener;
//import iqq.im.WebQQClient;
//import iqq.im.actor.ThreadActorDispatcher;
//import iqq.im.bean.QQGroup;
//import iqq.im.bean.QQStatus;
//import iqq.im.event.QQActionEvent;
//import iqq.im.event.QQActionFuture;
//import iqq.im.event.QQNotifyEvent;
//import iqq.im.event.QQNotifyEventArgs;
//import sun.misc.BASE64Encoder;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.*;
//
///**
// * Client测试类
// *
// * @author solosky
// */
//public class WebQQClientService {
//  public static void main(String[] args) throws QQException {
//    new WebQQClientService().login("1142089526","nopass_2","");
//  }
//
//
//  QQClient client;
//
//  public String imageToBase64(BufferedImage bi) throws IOException {
//    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    ImageIO.write(bi, "jpg", baos);
//    byte[] bytes = baos.toByteArray();
//    BASE64Encoder encoder = new sun.misc.BASE64Encoder();
//    return encoder.encodeBuffer(bytes).trim();
//  }
//
//  String base64;
//
//  public String login(String user, String password,String verifyCode) throws QQException {
//
//    client = new WebQQClient(user, password, new QQNotifyListener() {
//      public void onNotifyEvent(QQNotifyEvent event) {
//        System.out.println("QQNotifyEvent: " + event.getType() + ", " + event.getTarget());
//        if (event.getType() == QQNotifyEvent.Type.CAPACHA_VERIFY) {
//          try {
//            QQNotifyEventArgs.ImageVerify verify = (QQNotifyEventArgs.ImageVerify) event.getTarget();
//            base64 = imageToBase64(verify.image);
//
//            ImageIO.write(verify.image, "png", new File("verify.png"));
//            System.out.println(verify.reason);
//            System.out.print("请输入在项目根目录下verify.png图片里面的验证码:");
//            String code = new BufferedReader(new InputStreamReader(System.in)).readLine();
//            client.submitVerify(code, event);
//          } catch (IOException e) {
//          }
//        }
//      }
//    }, new ThreadActorDispatcher());
//
//    //测试同步模式登录
//    QQActionFuture future = client.login(QQStatus.ONLINE, null);
//    QQActionEvent event = future.waitFinalEvent();
//    if (event.getType() == QQActionEvent.Type.EVT_OK) {
//      System.out.println("登录成功！！！！");
//
//    } else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
//      QQException ex = (QQException) event.getTarget();
//      if (ex.getError() == QQException.QQErrorCode.NEED_CAPTCHA) {
//        //TODO ..
//        System.out.println("需要验证码！！！");
//      }
//    }
//
//    return null;
//  }
//
//  public void group() throws QQException {
//    client.getGroupList(null).waitFinalEvent();
//    System.out.println("Buddy count: " + client.getGroupList().size());
//    for (QQGroup group : client.getGroupList()) {
//      System.out.println(group);
//    }
//  }
//
//}
