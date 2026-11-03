// code by jph
package ch.alpine.bridge.geo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HexFormat;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;

class TileTest {
  @Test
  void test() {
    assertEquals(HexFormat.of().toHexDigits(63), "0000003f");
    assertEquals(HexFormat.of().toHexDigits(63L), "000000000000003f");
  }

  @Test
  void testLatLon() {
    Tile tile = new Tile(0, 0, 0);
    Tensor polygon = tile.cbb_lon_lat();
    CoordinateBoundingBox cbb = CoordinateBounds.of(polygon);
    Tolerance.CHOP.requireClose(cbb.clip(0).length(), Pi.TWO);
    Tolerance.CHOP.requireClose(cbb.clip(1).length(), RealScalar.of(2.968844459490665));
    
  }

  @Test
  void testThrows() {
    new Tile(0, 0, 0);
    assertThrows(Exception.class, () -> new Tile(-1, 0, 0));
    assertThrows(Exception.class, () -> new Tile(0, 1, 0));
    assertThrows(Exception.class, () -> new Tile(0, 0, 1));
  }
}
