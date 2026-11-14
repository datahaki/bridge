// code by jph
package ch.alpine.bridge.geo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.qty.Degree;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.UnitSystem;
import ch.alpine.tensor.sca.Chop;
import ch.alpine.tensor.sca.Clip;

class TilePixelTest {
  @Test
  void test() {
    TilePixel tilePixel = TilePixel.from(0, 256, RealScalar.of(0), RealScalar.of(0));
    TilePixel tileExpec = new TilePixel(new Tile(0, 0, 0), 256, 128, 128);
    assertEquals(tileExpec, tilePixel);
  }

  @Test
  void testAspe9() {
    Scalar lon = Quantity.of(-0.762800, Degree.UNIT);
    Scalar lat = Quantity.of(38.343373, Degree.UNIT);
    TilePixel tileP1 = TilePixel.from(9, 256, lon, lat);
    assertEquals(tileP1.tile().x(), 254);
    assertEquals(tileP1.tile().y(), 196);
  }

  @Test
  void testAspe() {
    Scalar lon = Quantity.of(-0.762800, Degree.UNIT);
    Scalar lat = Quantity.of(38.343373, Degree.UNIT);
    TilePixel tileP1 = TilePixel.from(17, 256, lon, lat);
    Tensor coords = tileP1.lon_lat();
    Tensor expect = Tensors.of(lon, lat).maps(UnitSystem.SI());
    Chop._05.requireClose(coords, expect);
    TilePixel tileP2 = TilePixel.from(17, 256, Tensors.of(lon, lat));
    assertEquals(tileP1, tileP2);
    TilePixel tileP3 = tileP2.from(Tensors.of(lon, lat));
    assertEquals(tileP1, tileP3);
    TilePixel tileP4 = tileP1.widIncr(-256, 19).orElseThrow();
    assertEquals(tileP4.tile().z(), 16);
    TilePixel tileP5 = tileP4.widIncr(+256, 19).orElseThrow();
    assertEquals(tileP5.tile().z(), 17);
  }

  @Test
  void testMax() {
    Clip clip = TilePixel.LAT_DOMAIN;
    Tolerance.CHOP.requireClose(clip.max(), RealScalar.of(1.4844222297453324));
  }

  @Test
  void testZeros() {
    Tensor coords = new TilePixel(new Tile(0, 0, 0), 256, 128, 128).lon_lat();
    Tolerance.CHOP.requireAllZero(coords); // lon
  }

  @Test
  void testWest() {
    Tensor coords = new TilePixel(new Tile(0, 0, 0), 256, 0, 128).lon_lat();
    Tolerance.CHOP.requireClose(coords, Tensors.vector(-Math.PI, 0));
  }

  @Test
  void testEast() {
    Tensor coords = new TilePixel(new Tile(0, 0, 0), 256, 256, 128).lon_lat();
    Tolerance.CHOP.requireClose(coords, Tensors.vector(Math.PI, 0));
  }

  @Test
  void testNorth() {
    Tensor coords = new TilePixel(new Tile(0, 0, 0), 256, 128, 0).lon_lat();
    double angle = TilePixel.LAT_DOMAIN.max().number().doubleValue();
    Tolerance.CHOP.requireClose(coords, Tensors.vector(0, angle));
  }

  @Test
  void testSouth() {
    Tensor coords = new TilePixel(new Tile(0, 0, 0), 256, 128, 256).lon_lat();
    double angle = TilePixel.LAT_DOMAIN.min().number().doubleValue();
    Tolerance.CHOP.requireClose(coords, Tensors.vector(0, angle));
  }

  @Test
  void testHiLo() {
    Tensor coordsHi = new TilePixel(new Tile(19, 0, 0), 256, 0, 0).lon_lat();
    Tensor coordsLo = new TilePixel(new Tile(0, 0, 0), 256, 0, 256).lon_lat();
    Chop._10.requireClose(coordsHi.Get(1), coordsLo.Get(1).negate());
  }
}
