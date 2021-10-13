// code by jph
package ch.alpine.java.awt;

import javax.swing.JPopupMenu;

import ch.alpine.tensor.mat.re.Pivots;
import junit.framework.TestCase;

public class SpinnerMenuTest extends TestCase {
  public void testSimple() {
    SpinnerLabel<Pivots> spinnerLabel = SpinnerLabel.of(Pivots.values());
    SpinnerMenu<Pivots> spinnerMenu = new SpinnerMenu<>(spinnerLabel, false);
    spinnerMenu.design(new JPopupMenu());
  }
}
