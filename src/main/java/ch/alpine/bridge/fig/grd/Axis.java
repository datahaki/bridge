// code by legion
package ch.alpine.bridge.fig.grd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.cal.DateTimeInterval;
import ch.alpine.bridge.fig.ShowOptions;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.bridge.math.Ticks;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.sca.Clip;

public abstract class Axis {
  // TODO 100 is a magic constant that should depend on font, and date formatter
  private static final int REF = 100;
  private static final Stroke STROKE_GRIDLINES = //
      new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
  private static final Color COLOR_GRIDLINES = new Color(128, 128, 128, 64);
  private static final Color COLOR_HELPER = new Color(192, 192, 192);
  private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(new AffineTransform(), true, true);
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
    Clip ticks_clip = axisConfig.ticks_clip();
    if (ticks_clip.isNonDegenerate()) {
      Map<Integer, String> map = new HashMap<>();
      if (ticks_clip.min() instanceof DateTime) {
        DateTimeInterval dateTimeInterval = //
            DateTimeInterval.findAboveEquals(ticks_clip.length().multiply(Rational.of(REF, axisConfig.pixel_len())));
        DateTime startAttempt = dateTimeInterval.floor(ticks_clip.min());
        DateTime dateTime = ticks_clip.isInside(startAttempt) //
            ? startAttempt
            : dateTimeInterval.plus(startAttempt);
        DateTimeFormatter dateTimeFormatter = axisOptions.dateTimeFocus.focus(dateTimeInterval.getSmallestDefined());
        while (ticks_clip.isInside(dateTime)) {
          int pixel = (int) axisConfig.ticks2pixel(dateTime);
          map.put(pixel, dateTime.format(dateTimeFormatter));
          dateTime = dateTimeInterval.plus(dateTime);
        }
      } else {
        int val = 50;
        int rem = axisConfig.pixel_len() / 3;
        if (this instanceof AxisY && rem < val) {
          LineMetrics lineMetrics = font.getLineMetrics("Ag", FONT_RENDER_CONTEXT);
          val = Math.max((int) Math.ceil(lineMetrics.getAscent() + lineMetrics.getDescent()), rem);
        }
        Ticks.stream(ticks_clip, Rational.of(val, axisConfig.pixel_len())) //
            .forEach(tick -> map.put((int) axisConfig.ticks2pixel(tick), axisConfig.format(tick)));
      }
      RenderQuality.smoothLine(graphics, false);
      Rectangle rectangle = showableConfig.rectangle();
      if (axisOptions.contains(AxisOption.GRID)) { // grid lines | or _
        graphics.setStroke(STROKE_GRIDLINES);
        graphics.setColor(COLOR_GRIDLINES);
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
