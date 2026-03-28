// code by jph
package ch.alpine.bridge.io.ani;

import java.awt.image.BufferedImage;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.ext.Jpeg;

/** for use MP4
 * internally converts images to 3-byte BGR since this is accepted by */
public record BGR3AnimationWriter(AnimationWriter animationWriter) implements AnimationWriter {
  @Override // from AnimationWriter
  public void write(BufferedImage bufferedImage) throws Exception {
    animationWriter.write(Jpeg.bgr(bufferedImage, BufferedImage.TYPE_3BYTE_BGR));
  }

  @Override // from AnimationWriter
  public void write(Tensor tensor) throws Exception {
    animationWriter.write(tensor);
  }

  @Override // from AnimationWriter
  public void close() throws Exception {
    animationWriter.close();
  }
}
