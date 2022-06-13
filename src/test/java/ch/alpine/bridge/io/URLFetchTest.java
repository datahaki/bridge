// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.ext.HomeDirectory;

class URLFetchTest {
  @Test
  void testSimple(@TempDir File tempDir) throws MalformedURLException, IOException {
    if (TestHelper.IS_ONLINE) {
      File file = new File(tempDir, "file.ico");
      try (URLFetch urlFetch = new URLFetch(new URL("http://www.hakenberg.de/favicon.ico"))) {
        assertEquals(urlFetch.length(), 1406);
        assertEquals(urlFetch.contentType(), "image/x-icon");
        urlFetch.downloadIfMissing(file);
      }
      assertEquals(file.length(), 1406);
      file.delete();
      assertFalse(file.exists());
    }
  }

  @Test
  void testNoFileFail() {
    if (TestHelper.IS_ONLINE)
      try {
        try (URLFetch urlFetch = new URLFetch(new URL("http://www.hakenberg.de/doesnotexist.file.unknown"))) {
          fail();
        }
      } catch (Exception exception) {
        // ---
      }
  }

  @Test
  void testInputStream() throws MalformedURLException, IOException {
    BufferedImage bufferedImage = null;
    if (TestHelper.IS_ONLINE)
      try (URLFetch urlFetch = new URLFetch(new URL("http://www.hakenberg.de/_images/icon.bik.png"))) {
        assertEquals(urlFetch.length(), 481);
        try (InputStream inputStream = urlFetch.inputStream()) {
          bufferedImage = ImageIO.read(inputStream);
        }
        assertEquals(bufferedImage.getHeight(), 16);
        assertEquals(bufferedImage.getWidth(), 16);
      }
  }

  @Test
  void testDuplicate(@TempDir File tempDir) throws IOException {
    if (TestHelper.IS_ONLINE) {
      File file = new File(tempDir, "file.ico");
      try (URLFetch urlFetch = new URLFetch(new URL("http://www.hakenberg.de/favicon.ico"))) {
        urlFetch.downloadIfMissing(file);
        urlFetch.downloadIfMissing(file);
        try {
          urlFetch.download(HomeDirectory.Downloads("download.that.never.started"));
          fail();
        } catch (Exception exception) {
          // ---
        }
      }
      assertEquals(file.length(), 1406);
    }
  }
}
