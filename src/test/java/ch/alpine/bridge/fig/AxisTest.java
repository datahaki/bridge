// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

class AxisTest {
  @Test
  void testSimple() {
    assertEquals(new Axis().getUnitString(), "");
    Axis axis = new Axis();
    axis.setUnit(Unit.ONE);
    assertEquals(axis.getUnitString(), "");
  }

  @Test
  void testMap() {
    Clip clip = Axis.slash(Clips.interval(Quantity.of(3, "km"), Quantity.of(4, "km")), UnitConvert.SI().to("mi"));
    assertEquals(clip.toString(), "Clip[15625/8382[mi], 31250/12573[mi]]");
  }
}
