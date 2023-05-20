package gbl.util.log;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

/**
 *
 */
public class LoggerUtil {


  /**
   * debugLogger
   */
  public static void debug(Object msg) {
    Logger.getLogger("debugLogger").debug(msg);
  }



  /**
   * 记录本系统调用外部系统接口的Logger
   */
  public static void inOutInfo(String msg) {
    Logger.getLogger("inOutLogger").info(msg);
  }

  /**
   * 记录外部系统调用本系统接口的Logger
   */
  public static void outInInfo(String msg) {
    Logger.getLogger("outInLogger").info(msg);
  }

  /**
   * 缓存存取的Logger
   */
  public static void cacheInfo(Object msg) {
    Logger.getLogger("cacheLogger").info(msg);
  }

  /**
   * 缓存存取的Logger --记录异常信息
   */
  public static void cacheInfo(Object msg, Throwable throwable) {
    Logger.getLogger("cacheLogger").info(msg);
    error(msg.toString(), throwable);
  }



  /**
   * errorLogger
   */
  public static void error(String msg, Exception e) {
    printError(Logger.getLogger("errorLogger"), msg, e);
  }


  /**
   * errorLogger
   */
  public static void error(String msg, Throwable e) {

    printError(Logger.getLogger("errorLogger"), msg, e);
  }

  public static void printError(Logger logger, String msg, Throwable e) {
    if (msg != null) {
      logger.error(msg);
    }
    if (e != null) {
      ByteArrayOutputStream buf = new ByteArrayOutputStream();
      e.printStackTrace(new PrintWriter(buf, true));
      logger.error(buf.toString());
    }
  }

}
