// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.ext.HomeDirectory;

class URLFetchTest {
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
  void testSimple(@TempDir Path tempDir) throws IOException, URISyntaxException {
    Path file = tempDir.resolve("file.ico");
    try (URLFetch urlFetch = new URLFetch("http://www.hakenberg.de/favicon.ico")) {
      assertEquals(urlFetch.length(), 1406);
      assertEquals(urlFetch.contentType(), "image/x-icon");
      urlFetch.downloadIfMissing(file);
    }
    assertEquals(Files.size(file), 1406);
    Files.delete(file);
    assertFalse(Files.isRegularFile(file));
  }

  @Test
  void testNoFileFail() {
    try {
      try (URLFetch _ = new URLFetch("http://www.hakenberg.de/doesnotexist.file.unknown")) {
        fail();
      }
    } catch (Exception exception) {
      // ---
    }
  }

  @Test
  void testInputStream() throws IOException, URISyntaxException {
    try (URLFetch urlFetch = new URLFetch("http://www.hakenberg.de/_images/icon.bik.png")) {
      assertEquals(urlFetch.length(), 481);
      BufferedImage bufferedImage = null;
      try (InputStream inputStream = urlFetch.inputStream()) {
        bufferedImage = ImageIO.read(inputStream);
      }
      assertEquals(bufferedImage.getHeight(), 16);
      assertEquals(bufferedImage.getWidth(), 16);
    }
  }

  @Test
  void testDuplicate(@TempDir Path tempDir) throws IOException, URISyntaxException {
    Path file = tempDir.resolve("file.ico");
    try (URLFetch urlFetch = new URLFetch("http://www.hakenberg.de/favicon.ico")) {
      urlFetch.downloadIfMissing(file);
      urlFetch.downloadIfMissing(file);
      assertThrows(Exception.class, () -> urlFetch.download(HomeDirectory.Downloads.resolve("download.that.never.started")));
    }
    assertEquals(Files.size(file), 1406);
  }
}
