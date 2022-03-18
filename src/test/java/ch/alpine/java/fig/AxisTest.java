// code by jph
package ch.alpine.java.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public class AxisTest {
  @Test
  public void testSimple() {
    assertEquals(new Axis().getUnitString(), "");
    Axis axis = new Axis();
    axis.setUnit(Unit.ONE);
    assertEquals(axis.getUnitString(), "");
  }

  @Test
  public void testUnit() {
    Axis axis = new Axis();
    axis.setUnit(Unit.of("m"));
    assertEquals(axis.getUnitString(), "[m]");
    axis.toReals().apply(Quantity.of(1, "km"));
    AssertFail.of(() -> axis.toReals().apply(Quantity.of(1, "A")));
  }

  @Test
  public void testMap() {
    Clip clip = Axis.slash(Clips.interval(Quantity.of(3, "km"), Quantity.of(4, "km")), UnitConvert.SI().to("mi"));
    assertEquals(clip.toString(), "Clip[15625/8382[mi], 31250/12573[mi]]");
  }
}
