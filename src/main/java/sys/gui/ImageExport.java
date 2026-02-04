// code by jph
package sys.gui;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import ch.alpine.tensor.ext.FileExtension;

public enum ImageExport {
  ;
  public static void of(File file, BufferedImage bufferedImage) {
    try {
      ImageIO.write(bufferedImage, FileExtension.of(file), file);
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }
}
