package tk.gbl.agent.service;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import tk.gbl.agent.request.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Date: 2015/4/16
 * Time: 21:53
 *
 * @author Tian.Dong
 */
public class SimBaServiceClient {

  private static JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
  private static SimBaService service;
  private static String appId = "5730";

  static {
    try {
      init();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void init() throws IOException {
//    String address = "http://58.22.60.28:8080/AppCenter/services/AppCenterWS?wsdl";
    Properties prop = new Properties();
    InputStream is = SimBaServiceClient.class.getResourceAsStream("/simba.properties");
    prop.load(is);
    String address = prop.getProperty("webservice.url");
    factory.setServiceClass(SimBaService.class);
    factory.setAddress(address);
    service = (SimBaService) factory.create();
  }

  public static String accountLogin(AccountLoginRequest request) {
    String result = service.request(appId, request.toString());
    return result;
  }

  public static String tokenLogin(TokenLoginRequest request) {
    String result = service.request(appId, request.toString());
    return result;
  }

  public static String getCustNbrInfo(GetCustNbrInfoRequest request) {
    String result = service.request(appId, request.toString());
    return result;
  }

  public static String messageNotify(MessageNotifyRequest request) {
    String result = service.request(appId, request.toString());
    return result;
  }

  public static String orgService(OrgServiceRequest request) {
    String result = service.request(appId, request.toString());
    return result;
  }

  public static String orgServiceVersion(GetOrgVersionRequest request) {
    String result = service.request(appId, request.toString());
    return result;
  }

  public static String userStatus(UserStatusRequest request) {
    String result = service.request(appId, request.toString());
    return result;
  }

  public static void main(String[] args) {
    try {
      init();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
