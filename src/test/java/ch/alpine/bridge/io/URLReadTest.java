// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class URLReadTest {
  public static final boolean IS_ONLINE = isOnline();

  /** Reference:
   * https://www.tutorialspoint.com/Checking-internet-connectivity-in-Java
   * 
   * @return */
  public static boolean isOnline() {
    try {
      URL url = new URI("http://www.google.com").toURL();
      URLConnection urlConnection = url.openConnection();
      urlConnection.connect();
      urlConnection.getInputStream().close();
      return true;
    } catch (Exception exception) {
      return false;
    }
  }

  @BeforeAll
  static void checkStatus() {
    assumeTrue(IS_ONLINE);
  }

  @Test
  void testSimple() throws IOException, URISyntaxException, InterruptedException {
    byte[] data = URLRead.of("http://www.hakenberg.de/favicon.ico");
    assertEquals(data.length, 1406);
  }

  @Test
  void testNoFileFail() throws IOException, InterruptedException, URISyntaxException {
    URLRead.of("http://www.hakenberg.de/doesnotexist.file.unknown");
  }

  @Test
  void testInputStream() throws IOException, URISyntaxException, InterruptedException {
    byte[] data = URLRead.of("http://www.hakenberg.de/_images/icon.bik.png");
    assertEquals(data.length, 481);
  }
}
