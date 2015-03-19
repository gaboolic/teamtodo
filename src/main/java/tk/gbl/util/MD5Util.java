package tk.gbl.util;

import tk.gbl.util.log.LoggerUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Date: 2015/3/2
 * Time: 16:30
 *
 * @author Tian.Dong
 */
public class MD5Util {

  public static String md5(String plainText) {
    try {
      return HEXAndMd5(plainText.getBytes("UTF8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String HEXAndMd5(byte[] plainText) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(plainText);
      byte b[] = md.digest();
      int i;
      StringBuffer buf = new StringBuffer(200);
      for (int offset = 0; offset < b.length; offset++) {
        i = b[offset] & 0xff;
        if (i < 16) buf.append("0");
        buf.append(Integer.toHexString(i));
      }
      return buf.toString();
    } catch (NoSuchAlgorithmException e) {
      LoggerUtil.error("Md5加密异常", e);
      return null;
    }
  }
}
