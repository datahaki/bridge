// code by jph
package ch.alpine.java.io;

import java.net.URL;
import java.net.URLConnection;

enum TestHelper {
  ;
  public static final boolean IS_ONLINE = isOnline();

  /** Reference:
   * https://www.tutorialspoint.com/Checking-internet-connectivity-in-Java
   * 
   * @return */
  public static boolean isOnline() {
    try {
      URL url = new URL("http://www.google.com");
      URLConnection urlConnection = url.openConnection();
      urlConnection.connect();
      return true;
    } catch (Exception exception) {
      return false;
    }
  }
}
