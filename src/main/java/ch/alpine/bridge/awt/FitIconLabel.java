// adapted from chatgpt
package ch.alpine.bridge.awt;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.JLabel;

 class FitIconLabel extends JLabel {
  public FitIconLabel(Icon icon) {
    super(icon);
    setHorizontalAlignment(CENTER);
    setVerticalAlignment(CENTER);
  }

  @Override
  protected void paintComponent(Graphics g) {
    Icon icon = getIcon();
    if (icon == null)
      super.paintComponent(g);
    else {
      Graphics2D graphics = (Graphics2D) g.create();
      int labelWidth = getWidth();
      int labelHeight = getHeight();
      int iconWidth = icon.getIconWidth();
      int iconHeight = icon.getIconHeight();
      // Compute scale factor (preserve aspect ratio)
      double scaleX = (double) labelWidth / iconWidth;
      double scaleY = (double) labelHeight / iconHeight;
      double scale = Math.min(scaleX, scaleY);
      int x = (int) ((labelWidth - iconWidth * scale) / 2);
      int y = (int) ((labelHeight - iconHeight * scale) / 2);
      graphics.translate(x, y);
      graphics.scale(scale, scale);
      icon.paintIcon(this, graphics, 0, 0);
      graphics.dispose();
    }
  }
}
