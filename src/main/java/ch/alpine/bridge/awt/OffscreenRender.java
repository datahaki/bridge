// code by jph
package ch.alpine.bridge.awt;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public enum OffscreenRender {
  ;
  public static BufferedImage of(JComponent jComponent) {
    return of(jComponent, jComponent.getSize());
  }

  public static BufferedImage of(JComponent jComponent, Dimension dimension) {
    BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    jComponent.printAll(graphics);
    graphics.dispose();
    return bufferedImage;
  }
}
