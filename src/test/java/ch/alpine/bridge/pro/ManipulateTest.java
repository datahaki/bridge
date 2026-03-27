// code by jph
package ch.alpine.bridge.pro;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

class ManipulateTest {
  @Test
  void test() {
    JFrame jFrame = Manipulate.asFrame(new Object(), () -> new JLabel());
    jFrame.setVisible(true);
    jFrame.setVisible(false);
  }
}
