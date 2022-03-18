// code by jph
package ch.alpine.javax.swing;

import javax.swing.JPopupMenu;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.mat.re.Pivots;

public class SpinnerMenuTest {
  @Test
  public void testSimple() {
    SpinnerLabel<Pivots> spinnerLabel = SpinnerLabel.of(Pivots.values());
    new SpinnerMenu<>(spinnerLabel, false).design(new JPopupMenu());
    new SpinnerMenu<>(spinnerLabel, true).design(new JPopupMenu());
  }
}
