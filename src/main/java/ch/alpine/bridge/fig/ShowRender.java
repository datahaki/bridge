// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import java.util.Objects;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

record ShowRender(List<Showable> showables, ShowOptions showOptions, CoordinateBoundingBox cbb) {
  private static final Color COLOR_FRAME = new Color(160, 160, 160);

  /** @param graphics
   * @param rectangle
   * @return null if input rectangle is unsuitable for drawing */
  public ShowableConfig render(Graphics _g, Rectangle rectangle) {
    Graphics2D graphics = (Graphics2D) _g.create();
    RenderQuality.setQuality(graphics);
    if (showOptions.contains(ShowOption.FRAMED)) {
      // draw box around ...
      RenderQuality.smoothLine(graphics, false);
      graphics.setStroke(StaticHelper.STROKE_SOLID);
      graphics.setColor(COLOR_FRAME);
      graphics.drawRect(rectangle.x - 1, rectangle.y - 1, rectangle.width + 1, rectangle.height + 1);
      RenderQuality.smoothLine(graphics, true);
    }
    {
      if (!showOptions.plotLabel.isEmpty()) {
        Font font = graphics.getFont().deriveFont(Font.BOLD);
        graphics.setFont(font);
        graphics.setColor(StaticHelper.COLOR_FONT);
        graphics.drawString(showOptions.plotLabel, rectangle.x, rectangle.y - StaticHelper.GAP);
      }
    }
    final ShowableConfig showableConfig;
    if (Objects.isNull(cbb)) {
      showableConfig = null;
      graphics.setColor(Color.DARK_GRAY);
      FontMetrics fontMetrics = graphics.getFontMetrics();
      String string = "no data";
      double delta_y = (fontMetrics.getAscent() - fontMetrics.getDescent()) * 0.5;
      graphics.drawString(string, //
          rectangle.x + (rectangle.width - fontMetrics.stringWidth(string)) / 2, //
          rectangle.y + (int) (rectangle.height * 0.5 + delta_y));
    } else {
      showableConfig = showOptions.contains(ShowOption.DECR_Y) //
          ? ShowableConfig.yDecr(rectangle, cbb)
          : ShowableConfig.yIncr(rectangle, cbb);
      // ---
      ShowableConfig showableConfigClipped = showableConfig.clipped();
      for (Showable showable : showables)
        if (showable instanceof BackgroundPlotMarker) {
          Graphics2D g = (Graphics2D) graphics.create(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
          showable.render(showableConfigClipped, g);
          g.dispose();
          showable.tender(showableConfig, graphics);
        }
      new GridDrawer(showOptions).render(showableConfig, graphics);
      for (Showable showable : showables)
        if (!(showable instanceof BackgroundPlotMarker)) {
          Graphics2D g = (Graphics2D) graphics.create(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
          showable.render(showableConfigClipped, g);
          g.dispose();
          showable.tender(showableConfig, graphics);
        }
    }
    {
      FontMetrics fontMetrics = graphics.getFontMetrics();
      int size = fontMetrics.getHeight();
      int pix = rectangle.x + StaticHelper.GAP;
      final int ystart = rectangle.y + 2;
      {
        int piy = ystart;
        graphics.setColor(new Color(255, 255, 255, 192));
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
    return showableConfig;
  }
}
