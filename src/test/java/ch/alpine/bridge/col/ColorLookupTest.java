// code by jph
package ch.alpine.bridge.col;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.col.ColorDataGradients;

class ColorLookupTest {
  @Test
  void test() {
    ColorLookup.hsluv_lightness(100, 0.3);
    ColorLookup.decreasing(100, ColorDataGradients.ALPINE);
    ColorLookup.increasing(80, ColorDataGradients.ALPINE);
  }
}
