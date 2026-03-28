// code by jph
package ch.alpine.bridge.geo;

import java.net.URI;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class TileServersTest {
  @ParameterizedTest
  @EnumSource
  void testUri(TileServers ts) {
    URI uri = ts.uri(3, 2, 1);
    uri.toString();
  }
}
