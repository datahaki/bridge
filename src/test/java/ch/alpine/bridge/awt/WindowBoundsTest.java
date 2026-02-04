// code by jph
package ch.alpine.bridge.awt;

import java.io.File;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class WindowBoundsTest {
  // TODO BRIDGE this does not terminate properly on Windows
  @TempDir
  File tempDir;

  @Test
  void test() throws InterruptedException {
    JFrame jFrame = new JFrame();
    WindowBounds.persistent(jFrame, new File(tempDir, "window.properties"));
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
