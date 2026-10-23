// code by jph
package ch.alpine.bridge.geo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HexFormat;

import org.junit.jupiter.api.Test;

class TileTest {
  @Test
  void test() {
    assertEquals(HexFormat.of().toHexDigits(63), "0000003f");
    assertEquals(HexFormat.of().toHexDigits(63L), "000000000000003f");
  }

  @Test
  void testThrows() {
    new Tile(0, 0, 0);
    assertThrows(Exception.class, () -> new Tile(-1, 0, 0));
    assertThrows(Exception.class, () -> new Tile(0, 1, 0));
    assertThrows(Exception.class, () -> new Tile(0, 0, 1));
  }
}
