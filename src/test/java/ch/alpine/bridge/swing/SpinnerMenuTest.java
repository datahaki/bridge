// code by jph
package ch.alpine.bridge.swing;

import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.mat.re.Pivots;

class SpinnerMenuTest {
  @Test
  public void testSimple() throws InterruptedException {
    SpinnerMenu<Pivots> spinnerMenu = new SpinnerMenu<>(Arrays.asList(Pivots.values()), Pivots.ARGMAX_ABS, false);
    spinnerMenu.addSpinnerListener(type -> System.out.println(type));
    JFrame jFrame = new JFrame();
    JButton jButton = new JButton();
    jFrame.setContentPane(jButton);
    jFrame.setVisible(true);
    spinnerMenu.showRight(jButton);
    Thread.sleep(100);
    jFrame.setVisible(false);
  }

  @Test
  public void testNull() throws InterruptedException {
    SpinnerMenu<Pivots> spinnerMenu = new SpinnerMenu<>(Arrays.asList(Pivots.values()), null, false);
    spinnerMenu.addSpinnerListener(type -> System.out.println(type));
    JFrame jFrame = new JFrame();
    JButton jButton = new JButton();
    jFrame.setContentPane(jButton);
    jFrame.setVisible(true);
    spinnerMenu.showRight(jButton);
    Thread.sleep(100);
    jFrame.setVisible(false);
  }

  @Test
  public void testNonContains() throws InterruptedException {
    SpinnerMenu<ColorDataGradients> spinnerMenu = new SpinnerMenu<>(Arrays.asList(ColorDataGradients.ALPINE), ColorDataGradients.CLASSIC, false);
    spinnerMenu.addSpinnerListener(type -> System.out.println(type));
    JFrame jFrame = new JFrame();
    JButton jButton = new JButton();
    jFrame.setContentPane(jButton);
    jFrame.setVisible(true);
    spinnerMenu.showRight(jButton);
    Thread.sleep(100);
    jFrame.setVisible(false);
  }
}
