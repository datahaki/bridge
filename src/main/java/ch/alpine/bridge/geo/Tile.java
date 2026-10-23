// code by jph
package ch.alpine.bridge.geo;

import java.io.Serializable;

import ch.alpine.tensor.ext.Integers;

public record Tile(int z, int x, int y) implements Serializable {
  /** Example: for z == 3 the values for x and y are in the interval
   * [0, 1, ..., 8)
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
}
