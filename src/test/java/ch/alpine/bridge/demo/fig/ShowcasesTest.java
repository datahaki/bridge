// code by jph
package ch.alpine.bridge.demo.fig;

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

class ShowcasesTest {
  @TempDir
  Path tempDir;

  @ParameterizedTest
  @EnumSource
  void test(Showcases showcases) throws IOException {
    Show show = showcases.getShow();
    BufferedImage bufferedImage = show.image(new Dimension(400, 300));
    assertEquals(bufferedImage.getWidth(), 400);
    assertEquals(bufferedImage.getHeight(), 300);
    Jpeg.put(bufferedImage, tempDir.resolve(showcases.name() + ".jpg"), 0.9f);
  }
}
