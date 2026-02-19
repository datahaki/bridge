// code by jph
package ch.alpine.bridge.io;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.d.DiscreteUniformDistribution;

class ImageClipboardTest {
  @Test
  void test() {
    ImageIO.setUseCache(false);
    ImageClipboard.copy(ImageFormat.of(RandomVariate.of(DiscreteUniformDistribution.forArray(256), 10, 20, 4)));
  }
}
