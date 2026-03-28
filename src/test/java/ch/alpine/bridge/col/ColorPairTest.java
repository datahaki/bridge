// code by jph
package ch.alpine.bridge.col;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class ColorPairTest {
  @Test
  void test() {
    ColorPair colorPair = new ColorPair(new Color(12, 23, 34, 45), new Color(112, 123, 134, 145));
    ColorPair solid = colorPair.solid();
    solid.draw();
  }
}
