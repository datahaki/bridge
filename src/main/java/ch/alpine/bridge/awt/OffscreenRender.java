// code by jph
package ch.alpine.bridge.awt;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public enum OffscreenRender {
  ;
  /** @param container of non zero dimension, may be Window, JComponent, ...
   * @param imageType
   * @return BufferedImage with size of given containter
   * @throws Exception if given container has zero size */
  public static BufferedImage of(Container container, int imageType) {
    Dimension dimension = container.getSize();
    BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, imageType);
    Graphics2D graphics = bufferedImage.createGraphics();
    container.printAll(graphics);
    graphics.dispose();
    return bufferedImage;
  }

  /** @param container of non zero dimension, may be Window, JComponent, ...
   * @return BufferedImage with size of given containter
   * @throws Exception if given container has zero size */
  public static BufferedImage of(Container container) {
    return of(container, BufferedImage.TYPE_INT_ARGB);
  }
}
