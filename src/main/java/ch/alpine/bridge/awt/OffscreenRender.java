// code by jph
package ch.alpine.bridge.awt;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public enum OffscreenRender {
  ;
  public static BufferedImage of(Container container, int imageType) {
    Dimension dimension = container.getSize();
    BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, imageType);
    Graphics2D graphics = bufferedImage.createGraphics();
    container.printAll(graphics);
    graphics.dispose();
    return bufferedImage;
  }

  public static BufferedImage of(Container container) {
    return of(container, BufferedImage.TYPE_INT_ARGB);
  }
}
