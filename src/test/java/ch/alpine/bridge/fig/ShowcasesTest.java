// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.ext.Jpeg;

class ShowcasesTest {
  @ParameterizedTest
  @EnumSource
  void test(Showcases showDemos) throws IOException {
    Show show = showDemos.create();
    // Serialization.copy(show);
    BufferedImage bufferedImage = show.image(new Dimension(400, 300));
    assertEquals(bufferedImage.getWidth(), 400);
    assertEquals(bufferedImage.getHeight(), 300);
    File folder = HomeDirectory.Downloads("export");
    folder.mkdir();
    Jpeg.put(bufferedImage, new File(folder, showDemos.name()+".jpg"), 0.9f);
    // Serialization.copy(bufferedImage);
  }
}
