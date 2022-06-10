// code by jph
package ch.alpine.bridge.swing;

import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.mat.re.Pivots;

class SelectionMenuTest {
  @Test
  public void testSimple() throws InterruptedException {
    SelectionMenu<Pivots> selectionMenu = new SelectionMenu<>(Arrays.asList(Pivots.values()), Pivots.ARGMAX_ABS, new JTextArea().getFont(), false) {
      @Override
      public void update(Pivots type) {
        System.out.println(type);
      }
    };
    JFrame jFrame = new JFrame();
    JButton jButton = new JButton();
    jFrame.setContentPane(jButton);
    jFrame.setVisible(true);
    selectionMenu.showRight(jButton);
    Thread.sleep(100);
    jFrame.setVisible(false);
  }
}
