// code by jph
package ch.alpine.bridge.fig.grd;

import java.awt.Rectangle;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.geo.Tile;
import ch.alpine.bridge.geo.TilePixel;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

class AxisMercatorTest {
  @Test
  void test() {
    TilePixel origin = new TilePixel(new Tile(4, 7, 10), 256, 4, 123);
    AxisMercator axisMercator = new AxisMercator(origin);
    CoordinateBoundingBox cbb = axisMercator.cbb(new Rectangle(0, 0, 100, 100));
  }

  @Test
  void testSimple() {
    Tile tile = new Tile(4, 7, 10);
    CoordinateBoundingBox cbb1 = tile.cbb();
    TilePixel origin = new TilePixel(tile, 256, 0, 0);
    AxisMercator axisMercator = new AxisMercator(origin);
    CoordinateBoundingBox cbb2 = axisMercator.cbb(new Rectangle(0, 0, 256, 256));
    IO.println(cbb1);
    IO.println(cbb2);
  }
}
