// code by jph
package ch.alpine.bridge.awt;

import java.nio.file.Path;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;

class WindowBoundsTest {
  @TempDir
  Path tempDir;

  @Test
  @DisabledOnOs(OS.WINDOWS)
  void test() throws InterruptedException {
    JFrame jFrame = new JFrame();
    WindowBounds.persistent(jFrame, tempDir.resolve("window.properties"));
    jFrame.setVisible(true);
    Thread.sleep(100);
    jFrame.setVisible(false);
  }
}
