// code by jph
package ch.alpine.bridge.swing;

import java.util.Arrays;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.mat.re.Pivots;

class SpinnerMenuTest {
  @Test
  void testSimple() throws InterruptedException {
    SpinnerMenu<Pivots> spinnerMenu = new SpinnerMenu<>(Arrays.asList(Pivots.values()), Pivots.ARGMAX_ABS, Object::toString, false);
    spinnerMenu.addSpinnerListener(System.out::println);
    JFrame jFrame = new JFrame();
    JButton jButton = new JButton();
    jFrame.setContentPane(jButton);
    jFrame.setVisible(true);
    spinnerMenu.showRight(jButton);
    Thread.sleep(100);
    jFrame.setVisible(false);
  }

  @Test
  void testNull() throws InterruptedException {
    SpinnerMenu<Pivots> spinnerMenu = new SpinnerMenu<>(Arrays.asList(Pivots.values()), null, Object::toString, false);
    spinnerMenu.addSpinnerListener(Objects::requireNonNull);
    spinnerMenu.spinnerListeners_spun(Pivots.ARGMAX_ABS);
    JFrame jFrame = new JFrame();
    JButton jButton = new JButton();
    jFrame.setContentPane(jButton);
    jFrame.setVisible(true);
    spinnerMenu.showRight(jButton);
    Thread.sleep(100);
    jFrame.setVisible(false);
  }

  @Test
  void testNonContains() throws InterruptedException {
    SpinnerMenu<ColorDataGradients> spinnerMenu = new SpinnerMenu<>(Arrays.asList(ColorDataGradients.ALPINE), ColorDataGradients.CLASSIC, Object::toString,
        false);
    spinnerMenu.addSpinnerListener(System.out::println);
    JFrame jFrame = new JFrame();
    JButton jButton = new JButton();
    jFrame.setContentPane(jButton);
    jFrame.setVisible(true);
    spinnerMenu.showRight(jButton);
    Thread.sleep(100);
    jFrame.setVisible(false);
  }
}
