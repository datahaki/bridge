// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ShowDemoTest {
  @ParameterizedTest
  @EnumSource
  void test(ShowDemos showDemos) {
    Show show = showDemos.create();
    // Serialization.copy(show);
    BufferedImage bufferedImage = show.image(new Dimension(400, 300));
    assertEquals(bufferedImage.getWidth(), 400);
    assertEquals(bufferedImage.getHeight(), 300);
    // Serialization.copy(bufferedImage);
  }
}
