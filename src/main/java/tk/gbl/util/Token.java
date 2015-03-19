package tk.gbl.util;

import java.util.Calendar;
import java.util.TimeZone;

public final class Token {
  public static long getToken() {
    TimeZone bjTzone = TimeZone.getTimeZone("GMT+08:00");
    TimeZone dfTzone = TimeZone.getDefault();
    long off = bjTzone.getRawOffset() - dfTzone.getRawOffset();

    Calendar cr = Calendar.getInstance();
    return cr.getTimeInMillis() + off;
  }
}