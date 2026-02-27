// code by jph
package ch.alpine.bridge.pro;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

enum SolidIcon {
  ;
  static Icon create(Color color, int size) {
    BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    graphics.setColor(color);
    graphics.fillRect(0, 0, size, size);
    graphics.dispose();
    return new ImageIcon(bufferedImage);
  }
}
