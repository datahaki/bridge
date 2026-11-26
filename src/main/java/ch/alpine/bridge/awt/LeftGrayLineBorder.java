// code adapted from gemini
package ch.alpine.bridge.awt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.border.EmptyBorder;

public class LeftGrayLineBorder extends EmptyBorder {
  public LeftGrayLineBorder(int top, int left, int bottom, int right) {
    super(top, left, bottom, right);
  }

  @Override
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    if (0 < left) {
      Color previousColor = g.getColor();
      g.setColor(new Color(128, 128, 128, 128));
      int ofs = left / 2;
      g.drawLine(x + ofs, y, x + ofs, y + height - 1);
      g.setColor(previousColor);
    }
  }
}
