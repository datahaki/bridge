// code by jph
package ch.alpine.bridge.io.ani;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.Objects;

import javax.swing.ImageIcon;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.io.ImageFormat;

public class ImageIconRecorder {
  /** @param duration of a single frame
   * @return */
  public static ImageIconRecorder loop(Duration duration) {
    return new ImageIconRecorder(duration, true);
  }

  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
  private final AnimatedGifWriter animatedGifWriter;
  private ImageIcon imageIcon = null;

  public ImageIconRecorder(Duration duration, boolean loop) {
    try {
      animatedGifWriter = AnimatedGifWriter.of(baos, duration, loop);
    } catch (IOException ioException) {
      throw new UncheckedIOException(ioException);
    }
  }

  public void write(BufferedImage bufferedImage) {
    try {
      animatedGifWriter.write(bufferedImage);
    } catch (IOException ioException) {
      throw new UncheckedIOException(ioException);
    }
  }

  public void write(Tensor tensor) {
    write(ImageFormat.of(tensor));
  }

  private void close() {
    if (Objects.nonNull(animatedGifWriter))
      try {
        animatedGifWriter.close();
      } catch (IOException ioException) {
        throw new UncheckedIOException(ioException);
      }
    imageIcon = new ImageIcon(baos.toByteArray());
  }

  public ImageIcon getIconImage() {
    close();
    return imageIcon;
  }
}
