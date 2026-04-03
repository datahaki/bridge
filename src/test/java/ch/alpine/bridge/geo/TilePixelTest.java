// code by jph
package ch.alpine.bridge.geo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.UnitSystem;
import ch.alpine.tensor.sca.Chop;
import ch.alpine.tensor.sca.Clip;

class TilePixelTest {
  @Test
  void test() {
    TilePixel tilePixel = TilePixel.from(0, RealScalar.of(0), RealScalar.of(0));
    TilePixel tileExpec = new TilePixel(new Tile(0, 0, 0), 128, 128);
    assertEquals(tileExpec, tilePixel);
  }

  @Test
  void testAspe() {
    Scalar lat = Quantity.of(38.343373, "deg");
    Scalar lon = Quantity.of(-0.762800, "deg");
    TilePixel tileP1 = TilePixel.from(17, lat, lon);
    Tensor coords = tileP1.lat_lon();
    Tensor expect = Tensors.of(lat, lon).maps(UnitSystem.SI());
    Chop._05.requireClose(coords, expect);
    TilePixel tileP2 = TilePixel.from(17, Tensors.of(lat, lon));
    assertEquals(tileP1, tileP2);
    TilePixel tileP3 = tileP2.from(Tensors.of(lat, lon));
    assertEquals(tileP1, tileP3);
    TilePixel tileP4 = tileP1.zoomIncr(-1);
    assertEquals(tileP4.tile().z(), 16);
    TilePixel tileP5 = tileP4.zoomIncr(+1);
    assertEquals(tileP5.tile().z(), 17);
  }

  @Test
  void testMax() {
    Clip clip = TilePixel.LAT_DOMAIN;
    Tolerance.CHOP.requireClose(clip.max(), RealScalar.of(1.4844222297453324));
  }

  @Test
  void testInvLat() {
    Tensor coords = new TilePixel(new Tile(0, 0, 0), 128, 128).lat_lon();
    Tolerance.CHOP.requireAllZero(coords); // lon
  }
}
