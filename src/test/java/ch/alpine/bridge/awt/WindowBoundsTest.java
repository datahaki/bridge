// code by jph
package ch.alpine.bridge.awt;

import java.nio.file.Path;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class WindowBoundsTest {
  @TempDir
  Path tempDir;

  // TODO BRIDGE this does not terminate properly on Windows
  @Disabled
  @Test
  void test() throws InterruptedException {
    JFrame jFrame = new JFrame();
    WindowBounds.persistent(jFrame, tempDir.resolve("window.properties"));
    jFrame.setVisible(true);
    Thread.sleep(100);
    jFrame.setVisible(false);
    jFrame.dispose();
    System.gc();
  }

  @AfterAll
  static void run() throws InterruptedException {
    Thread.sleep(100);
  }
}
