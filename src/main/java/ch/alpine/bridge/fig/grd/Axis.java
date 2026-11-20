// code by legion
package ch.alpine.bridge.fig.grd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.Map;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.fig.ShowOptions;
import ch.alpine.bridge.fig.ShowableConfig;

public abstract class Axis {
  private static final Stroke STROKE_GRIDLINES = //
      new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
  private static final Color COLOR_GRIDLINES = new Color(128, 128, 128, 64);
  private static final Color COLOR_HELPER = new Color(192, 192, 192);
  // ---
  protected final AxisOptions axisOptions;
  private Font font = new Font(Font.DIALOG, Font.PLAIN, 12);

  public Axis(AxisOptions axisOptions) {
    this.axisOptions = axisOptions;
  }

  /** @param showableConfig
   * @param point
   * @param graphics */
  public final void render(ShowableConfig showableConfig, Point point, Graphics2D graphics) {
    AxisConfig axisConfig = axisConfig(showableConfig);
    Map<Integer, String> map = axisConfig.getTicksMap(this instanceof AxisY, font, axisOptions.dateTimeFocus);
    RenderQuality.smoothLine(graphics, false);
    if (axisOptions.contains(AxisOption.GRID)) { // grid lines | or _
      graphics.setStroke(STROKE_GRIDLINES);
      graphics.setColor(COLOR_GRIDLINES);
      Rectangle rectangle = showableConfig.rectangle();
      for (int pixel : map.keySet())
        drawGridLine(graphics, rectangle, pixel);
    }
    if (axisOptions.contains(AxisOption.TICK)) {
      graphics.setStroke(ShowOptions.STROKE_SOLID);
      graphics.setColor(COLOR_HELPER);
      drawAxisLine(graphics, point, axisConfig.pixel_len());
      for (int piy : map.keySet())
        drawAxisTick(graphics, point, piy);
      graphics.setFont(font);
      protected_render(graphics, point, map);
    }
    RenderQuality.smoothLine(graphics, true);
  }

  protected abstract AxisConfig axisConfig(ShowableConfig showableConfig);

  /** @param graphics
   * @param rectangle
   * @param pixel */
  protected abstract void drawGridLine(Graphics2D graphics, Rectangle rectangle, int pixel);

  /** @param graphics
   * @param point */
  protected abstract void drawAxisLine(Graphics2D graphics, Point point, int pixel_len);

  /** @param graphics
   * @param point
   * @param pixel */
  protected abstract void drawAxisTick(Graphics2D graphics, Point point, int pixel);

  /** @param graphics
   * @param point */
  protected abstract void protected_render(Graphics2D graphics, Point point, Map<Integer, String> map);
}
