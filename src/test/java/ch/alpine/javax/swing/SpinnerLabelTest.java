// code by jph
package ch.alpine.javax.swing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

public class SpinnerLabelTest {
  @Test
  public void testSimple() {
    assertTrue(new JLabel("asd").getPreferredSize().getWidth() //
        < new JLabel("asd123123").getPreferredSize().getWidth());
  }
}
