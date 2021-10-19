// code by jph
package ch.alpine.java.fig;

import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;
import junit.framework.TestCase;

public class AxisTest extends TestCase {
  public void testSimple() {
    assertEquals(new Axis().getUnitString(), "");
    Axis axis = new Axis();
    axis.setUnit(Unit.ONE);
    assertEquals(axis.getUnitString(), "");
  }

  public void testUnit() {
    Axis axis = new Axis();
    axis.setUnit(Unit.of("m"));
    assertEquals(axis.getUnitString(), "[m]");
    axis.toReals().apply(Quantity.of(1, "km"));
    AssertFail.of(() -> axis.toReals().apply(Quantity.of(1, "A")));
  }
}
