// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;

public class ShowComponent extends JComponent {
  public static final Font FONT = new Font(Font.DIALOG, Font.PLAIN, 12);
  private final Show show;

  public ShowComponent(Show show) {
    this.show = show;
  }

  @Override
  protected void paintComponent(Graphics graphics) {
    // graphics.setFont(FONT);
    Dimension dimension = getSize();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, dimension.width, dimension.height);
    // TODO consider font when computing rectangle
    show.render(graphics, Show.defaultInsets(dimension));
  }
}
