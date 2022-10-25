// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class ShowComponent extends JComponent {
  private final Show show;

  public ShowComponent(Show show) {
    this.show = show;
  }

  @Override
  protected void paintComponent(Graphics graphics) {
    Dimension dimension = getSize();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, dimension.width, dimension.height);
    Insets insets = Show.defaultInsets();
    Rectangle rectangle = new Rectangle( //
        insets.left, //
        insets.top, //
        dimension.width - insets.left - insets.right, //
        dimension.height - insets.top - insets.bottom);
    show.render(graphics, rectangle);
  }
}
