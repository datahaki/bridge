// code by jph
package ch.alpine.bridge.io;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import javax.swing.ImageIcon;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.io.ImageFormat;

public class ImageIconRecorder {
  private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
  private final AnimatedGifWriter animatedGifWriter;
  private ImageIcon imageIcon = null;

  public ImageIconRecorder(int period, boolean loop) {
    try {
      animatedGifWriter = AnimatedGifWriter.of(baos, period, loop);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public ImageIconRecorder(int period) {
    this(period, true);
  }

  public void write(BufferedImage bufferedImage) {
    try {
      animatedGifWriter.write(bufferedImage);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public void write(Tensor tensor) {
    write(ImageFormat.of(tensor));
  }

  private void close() {
    if (Objects.nonNull(animatedGifWriter))
      try {
        animatedGifWriter.close();
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    imageIcon = new ImageIcon(baos.toByteArray());
  }

  public ImageIcon getIconImage() {
    close();
    return imageIcon;
  }
}
