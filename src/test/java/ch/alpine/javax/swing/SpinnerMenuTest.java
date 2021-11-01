// code by jph
package ch.alpine.javax.swing;

import javax.swing.JPopupMenu;

import ch.alpine.tensor.mat.re.Pivots;
import junit.framework.TestCase;

public class SpinnerMenuTest extends TestCase {
  public void testSimple() {
    SpinnerLabel<Pivots> spinnerLabel = SpinnerLabel.of(Pivots.values());
    new SpinnerMenu<>(spinnerLabel, false).design(new JPopupMenu());
    new SpinnerMenu<>(spinnerLabel, true).design(new JPopupMenu());
  }
}
