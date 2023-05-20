package tk.gbl.agent.service;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

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
    init();
  }

  public static void init() {
    String address = "http://58.22.60.28:8080/AppCenter/services/AppCenterWS?wsdl";
    factory.setServiceClass(SimBaService.class);
    factory.setAddress(address);
    service = (SimBaService) factory.create();
  }

  public static String tokenLogin() {
    String result = null;
    String xml1 = "\"<request type=\\\"login\\\" subtype=\\\"checkedToken\\\" msid=\\\"\\\">\\n\" +\n" +
        "        \"<message>  \\n\" +\n" +
        "        \"  <user token=\\\"ee99d537-2d39-47ea-b250-df713fe3846a\\\" />\\n\" +\n" +
        "        \"</message>\\n\" +\n" +
        "        \"</request>\"";
    String xml2 = "<request type=\"login\" subtype=\"accountLogin\" msid=\"\">\n" +
        "<message>  \n" +
        "  <user account=\"admin@abc.com\"  pwd=\"yITMxADM\" pwdtype=\"1\"/>\n" +
        "</message>\n" +
        "</request>";

    String xml3 = "<request type=\"user\" subtype=\"getStatus\" msid=\"\">\n" +
        "<token>A5F47EE701D742be8FA7AC5469CC4573</token>\n" +
        "<message>  \n" +
        "  <user acc_nbr=\"66220097\" />\n" +
        "</message>\n" +
        "</request>";
    result = service.request(appId,xml1);
    return result;
  }

  public static void main(String[] args) {
   System.out.println(tokenLogin());
  }

}
