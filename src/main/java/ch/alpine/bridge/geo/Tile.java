// code by jph
package ch.alpine.bridge.geo;

import ch.alpine.tensor.ext.Integers;

public record Tile(int z, int x, int y) {
  public static int maxExclusive(int z) {
    return 1 << z;
  }

  public static int maxInclusive(int z) {
    return maxExclusive(z) - 1;
  }

  public Tile {
    int max = maxInclusive(z);
    Integers.requireEquals(x, Math.min(Math.max(0, x), max));
    Integers.requireEquals(y, Math.min(Math.max(0, y), max));
  }
}
