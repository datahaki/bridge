// code by jph
package ch.alpine.curios.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.ext.Jpeg;
import ch.alpine.tensor.ext.Serialization;

class ShowcasesTest {
  @TempDir
  Path tempDir;

  @ParameterizedTest
  @EnumSource
  void test(Showcases showDemos) throws IOException, ClassNotFoundException {
    Show show = showDemos.getShow();
    Serialization.copy(show);
    BufferedImage bufferedImage = show.image(new Dimension(400, 300));
    assertEquals(bufferedImage.getWidth(), 400);
    assertEquals(bufferedImage.getHeight(), 300);
    Jpeg.put(bufferedImage, tempDir.resolve(showDemos.name() + ".jpg"), 0.9f);
  }
}
