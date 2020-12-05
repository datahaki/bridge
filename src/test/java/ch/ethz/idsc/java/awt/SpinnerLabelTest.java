// code by jph
package ch.ethz.idsc.java.awt;

import javax.swing.JLabel;

import junit.framework.TestCase;

public class SpinnerLabelTest extends TestCase {
  public void testSimple() {
    assertTrue(new JLabel("asd").getPreferredSize().getWidth() //
        < new JLabel("asd123123").getPreferredSize().getWidth());
  }
}
