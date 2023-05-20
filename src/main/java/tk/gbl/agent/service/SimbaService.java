package tk.gbl.agent.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Date: 2015/4/16
 * Time: 21:53
 *
 * @author Tian.Dong
 */
@WebService(name ="AppCenterWS", serviceName = "AppCenterWS", targetNamespace = "http://ws.appcenter.isimba.cn")
public interface SimBaService {

  /**
   * @param appid  业务系统通信密钥，由本接口服务提供
   * @param xmlBuf 请求的内容，XML格式字符串，UTF-8编码。所有操作的请求信息都在XML格式字符串中定义，
   *               请求内容格式参考如下的xml，实现不同的操作则参考“3.接口功能描述”中的具体接口调用。
   * @return xml
   */
  @WebResult(name = "return", targetNamespace = "http://ws.appcenter.isimba.cn")
  public String request(@WebParam(name = "appid", targetNamespace = "http://ws.appcenter.isimba.cn") String appid,
                        @WebParam(name = "xmlBuf", targetNamespace = "http://ws.appcenter.isimba.cn") String xmlBuf);


}
