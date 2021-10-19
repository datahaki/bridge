// code by jph
package ch.alpine.javax.swing;

import javax.swing.JLabel;

import junit.framework.TestCase;

public class SpinnerLabelTest extends TestCase {
  public void testSimple() {
    assertTrue(new JLabel("asd").getPreferredSize().getWidth() //
        < new JLabel("asd123123").getPreferredSize().getWidth());
  }
}
