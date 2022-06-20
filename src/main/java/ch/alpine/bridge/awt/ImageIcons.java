// code by jph
package ch.alpine.bridge.awt;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum ImageIcons {
  ;
  public static Icon create(Image image, int size) {
    BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    RenderQuality.setQuality(graphics);
    graphics.drawImage(image, 0, 0, size, size, null);
    return create(bufferedImage);
  }

  public static Icon create(Image image) {
    return new ImageIcon(image);
  }
}
