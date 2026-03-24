// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import ch.alpine.tensor.ext.Jpeg;
import ch.alpine.tensor.ext.PathName;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Rasterize.html">Rasterize</a> */
public record Rasterize(Show show, Dimension dimension) {
  /** @param type
   * @return */
  public BufferedImage image(int type) {
    BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, type);
    Graphics2D graphics = bufferedImage.createGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, dimension.width, dimension.height);
    show.render_autoIndent(graphics, new Rectangle(new Point(), dimension));
    graphics.dispose();
    return bufferedImage;
  }

  /** @return */
  public BufferedImage image() {
    return image(BufferedImage.TYPE_INT_ARGB);
  }

  private static final float JPG_QUALITY = 0.98f;

  /** @param path
   * @throws IOException */
  public void export(Path path) throws IOException {
    String string = PathName.of(path).extension().toLowerCase();
    switch (string) {
    case "jpg", "jpeg" -> Jpeg.put(image(BufferedImage.TYPE_3BYTE_BGR), path, JPG_QUALITY);
    default -> {
      try (OutputStream outputStream = Files.newOutputStream(path)) {
        ImageIO.write(image(), string, outputStream);
      }
    }
    }
  }
}
