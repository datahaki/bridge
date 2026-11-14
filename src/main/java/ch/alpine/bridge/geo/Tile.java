// code by jph
package ch.alpine.bridge.geo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.Integers;

/** the tiles cover latitudes in Clips.absolute ( 85.0511287798066[deg] ) */
public record Tile(int z, int x, int y) implements Serializable {
  /** Example: for z == 3 the values for x and y are in the interval
   * [0, 1, ..., 7]
   * 
   * @param z
   * @return end of interval [0, 1, ..., maxExclusive) for x and y to be inside */
  public static int maxExclusive(int z) {
    Integers.requirePositiveOrZero(z);
    return 1 << z;
  }

  /** Example: for z == 3 the values for x and y are in the interval
   * [0, 1, ..., 7]
   * 
   * @param z
   * @return end of interval [0, 1, ..., maxInclusive] for x and y to be inside */
  public static int maxInclusive(int z) {
    return maxExclusive(z) - 1;
  }

  /** @param z non-negative
   * @param x
   * @param y
   * @throws Exception if x and y are outside valid range defined by z */
  public Tile {
    int max = maxInclusive(z);
    Integers.requireEquals(x, Math.min(Math.max(0, x), max));
    Integers.requireEquals(y, Math.min(Math.max(0, y), max));
  }
  // TODO provide function that returns CBB

  /** @return polygon with positive area */
  public Tensor cbb_lon_lat() {
    return Tensors.of( //
        new TilePixel(this, 256, 0, 0).lon_lat(), //
        new TilePixel(this, 256, 0, 256).lon_lat(), //
        new TilePixel(this, 256, 256, 256).lon_lat(), //
        new TilePixel(this, 256, 256, 0).lon_lat());
  }

  public Collection<? extends Tile> children() {
    int z1 = z + 1;
    int x2 = x * 2;
    int y2 = y * 2;
    return List.of( //
        new Tile(z1, x2 + 0, y2 + 0), //
        new Tile(z1, x2 + 1, y2 + 0), //
        new Tile(z1, x2 + 0, y2 + 1), //
        new Tile(z1, x2 + 1, y2 + 1));
  }

  public Tile parent() {
    return new Tile(z - 1, x / 2, y / 2);
  }
}
