// code by jph
package ch.alpine.bridge.awt;

import java.io.File;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class WindowBoundsTest {
  @Test
  void test(@TempDir File tempDir) {
    JFrame jFrame = new JFrame();
    WindowBounds.persistent(jFrame, new File(tempDir, "window.properties"));
    jFrame.setVisible(true);
    jFrame.dispose();
  }
}
