// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

import ch.alpine.bridge.awt.RenderQuality;

record ShowRender(List<Showable> showables, ShowOptions showOptions, ShowableConfig showableConfig) {
  private static final Color COLOR_FRAME = new Color(160, 160, 160);
  private static final Color LEGEND_BACK = new Color(255, 255, 255, 192);

  /** @param graphics
   * @return null if input rectangle is unsuitable for drawing */
  public void render(Graphics _g) {
    Graphics2D graphics = (Graphics2D) _g.create();
    RenderQuality.setQuality(graphics);
    Rectangle rectangle = showableConfig.rectangle();
    if (showOptions.contains(ShowOption.FRAMED)) {
      /** draw frame/box around plot */
      RenderQuality.smoothLine(graphics, false);
      graphics.setStroke(StaticHelper.STROKE_SOLID);
      graphics.setColor(COLOR_FRAME);
      graphics.drawRect(rectangle.x - 1, rectangle.y - 1, rectangle.width + 1, rectangle.height + 1);
      RenderQuality.smoothLine(graphics, true);
    }
    {
      if (!showOptions.showLabel.isEmpty()) {
        /** draw title/show label on top */
        graphics.setFont(graphics.getFont().deriveFont(Font.BOLD));
        FontMetrics fontMetrics = graphics.getFontMetrics();
        graphics.setColor(StaticHelper.COLOR_FONT);
        graphics.drawString(showOptions.showLabel, rectangle.x, rectangle.y - fontMetrics.getDescent() - 1);
        graphics.setFont(graphics.getFont().deriveFont(Font.PLAIN));
      }
    }
    if (showableConfig.isEmpty()) {
      graphics.setColor(Color.DARK_GRAY);
      FontMetrics fontMetrics = graphics.getFontMetrics();
      String string = "no data";
      double delta_y = (fontMetrics.getAscent() - fontMetrics.getDescent()) * 0.5;
      // TODO extract functionality
      graphics.drawString(string, //
          rectangle.x + (rectangle.width - fontMetrics.stringWidth(string)) / 2, //
          rectangle.y + (int) (rectangle.height * 0.5 + delta_y));
    } else {
      ShowableConfig showablePruned = showableConfig.pruned();
      Graphics2D g_pruned = (Graphics2D) graphics.create(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
      for (Showable showable : showables)
        if (showable instanceof BackgroundPlotMarker) {
          showable.render(showablePruned, g_pruned);
          showable.tender(showableConfig, graphics);
        }
      new GridDrawer(showOptions) // requires drawing outside of rectangle
          .render(showableConfig, graphics);
      for (Showable showable : showables)
        if (!(showable instanceof BackgroundPlotMarker)) {
          showable.render(showablePruned, g_pruned);
          showable.tender(showableConfig, graphics);
        }
      g_pruned.dispose();
    }
    if (showOptions.contains(ShowOption.LEGEND)) {
      // TODO treat case: no data but legend !?
      FontMetrics fontMetrics = graphics.getFontMetrics();
      int size = fontMetrics.getHeight();
      int pix = rectangle.x + StaticHelper.GAP;
      final int ystart = rectangle.y + 2;
      {
        int piy = ystart;
        graphics.setColor(LEGEND_BACK);
        for (Showable showable : showables) {
          String string = showable.getLabel();
          if (!string.isEmpty()) {
            graphics.fillRect(pix, piy, fontMetrics.stringWidth(string), size);
            // showarea.setColor(Color.RED);
            // showarea.drawRect(pix, piy, fontMetrics.stringWidth(string), size);
            piy += size;
          }
        }
      }
      {
        int piy = ystart;
        for (Showable showable : showables) {
          String string = showable.getLabel();
          if (!string.isEmpty()) {
            piy += size;
            graphics.setColor(showable.getColor());
            graphics.drawString(string, pix, piy - 3);
          }
        }
      }
    }
    graphics.dispose();
  }
}
