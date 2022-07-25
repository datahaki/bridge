// code by clruch
package ch.alpine.bridge.swing.rs;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.junit.jupiter.api.Test;

class RangeSliderTest {
  @Test
  void test() throws ClassNotFoundException, InstantiationException, //
      IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException {
    /** look and feel */
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    /** start range slider */
    RangeSliderDemo rangeSliderDemo = new RangeSliderDemo();
    JFrame jFrame = rangeSliderDemo.display();
    /** allow time to play */
    Thread.sleep(5 * 1000);
    jFrame.dispose();
  }
}
