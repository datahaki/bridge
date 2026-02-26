// code by jph
package ch.alpine.bridge.swing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.mat.re.Pivots;

class SpinnerLabelTest {
  @Test
  void testSimple() {
    assertTrue(new JLabel("asd").getPreferredSize().getWidth() //
        < new JLabel("asd123123").getPreferredSize().getWidth());
  }

  @Test
  void testEnum() {
    SpinnerLabel.of(Pivots.class);
  }
}
