// code by jph
package ch.alpine.bridge.cal;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Tensors;

class GeoPositionTest {
  @Test
  void test() {
    System.out.println(GeoPosition.of(Tensors.vector(0, 90)));
  }
}
