// code by jph
package ch.alpine.bridge.io.ani;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.d.DiscreteUniformDistribution;

class ImageIconRecorderTest {
  @Test
  void test() {
    ImageIconRecorder imageIconRecorder = ImageIconRecorder.loop(Duration.ofMillis(250));
    for (int i = 0; i < 10; ++i) {
      Tensor rgba = RandomVariate.of(DiscreteUniformDistribution.forArray(256), 10, 20, 4);
      imageIconRecorder.write(ImageFormat.of(rgba));
    }
    imageIconRecorder.getIconImage();
  }
}
