// code by legion
package ch.alpine.bridge.fig;

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
import java.util.NavigableMap;
import java.util.TreeMap;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.cal.DateTimeInterval;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.sca.Clip;

abstract class Axis {
  // TODO BRIDGE 100 is a magic constant that should depend on font, and date formatter
  private static final int REF = 100;
  private static final Stroke STROKE_GRIDLINES = //
      new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
  private static final Color COLOR_GRIDLINES = new Color(128, 128, 128, 64);
  private static final Color COLOR_HELPER = new Color(192, 192, 192);
  private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(new AffineTransform(), true, true);
  // ---
  protected final ConfBase confBase;
  protected final AxisOptions showOptions;
  protected final NavigableMap<Integer, Scalar> navigableMap = new TreeMap<>();
  protected final DateTimeFormatter dateTimeFormatter;
  private Font font = new Font(Font.DIALOG, Font.PLAIN, 12);

  public Axis(ConfBase confBase, AxisOptions axisOptions) {
    this.confBase = confBase;
    this.showOptions = axisOptions;
    Clip clip = confBase.clip();
    if (clip.min() instanceof DateTime) {
      DateTimeInterval dateTimeInterval = //
          DateTimeInterval.findAboveEquals(clip.width().multiply(Rational.of(REF, confBase.length)));
      DateTime startAttempt = dateTimeInterval.floor(clip.min());
      DateTime dateTime = clip.isInside(startAttempt) //
          ? startAttempt
          : dateTimeInterval.plus(startAttempt);
      dateTimeFormatter = axisOptions.dateTimeFocus.focus(dateTimeInterval.getSmallestDefined());
      while (clip.isInside(dateTime)) {
        int pixel = (int) confBase.pixel(dateTime);
        navigableMap.put(pixel, dateTime);
        dateTime = dateTimeInterval.plus(dateTime);
      }
    } else {
      int val = 50;
      int rem = confBase.length / 3;
      if (this instanceof AxisY && rem < val) {
        LineMetrics lineMetrics = font.getLineMetrics("Ag", FONT_RENDER_CONTEXT);
        val = Math.max((int) Math.ceil(lineMetrics.getAscent() + lineMetrics.getDescent()), rem);
      }
      Ticks.stream(clip, Rational.of(val, confBase.length)) //
          .forEach(tick -> navigableMap.put((int) confBase.pixel(tick), tick));
      dateTimeFormatter = null;
    }
  }

  /** @param showableConfig
   * @param point
   * @param length
   * @param _g */
  protected final void render(ShowableConfig showableConfig, Point point, Graphics2D graphics) {
    if (Scalars.isZero(confBase.clip.width()))
      return;
    RenderQuality.smoothLine(graphics, false);
    Rectangle rectangle = showableConfig.rectangle();
    if (showOptions.contains(AxisOption.GRID)) { // grid lines |
      graphics.setStroke(STROKE_GRIDLINES);
      graphics.setColor(COLOR_GRIDLINES);
      for (int pixel : navigableMap.keySet())
        drawGridLine(graphics, rectangle, pixel);
    }
    if (showOptions.contains(AxisOption.TICK)) {
      graphics.setStroke(StaticHelper.STROKE_SOLID);
      graphics.setColor(COLOR_HELPER);
      drawAxisLine(graphics, point);
      for (int piy : navigableMap.keySet())
        drawAxisTick(graphics, point, piy);
      graphics.setFont(font);
      protected_render(graphics, point);
    }
    RenderQuality.smoothLine(graphics, true);
  }

  protected abstract void drawGridLine(Graphics2D graphics, Rectangle rectangle, int pixel);

  protected abstract void drawAxisLine(Graphics2D graphics, Point point);

  protected abstract void drawAxisTick(Graphics2D graphics, Point point, int pixel);

  protected abstract void protected_render(Graphics2D graphics, Point point);
}
