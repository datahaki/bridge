// code by legion
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.time.format.DateTimeFormatter;
import java.util.NavigableMap;
import java.util.TreeMap;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.cal.DateTimeInterval;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.sca.Clip;

abstract class Axis {
  protected static final Stroke STROKE_GRIDLINES = //
      new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
  protected static final Color COLOR_GRIDLINES = new Color(128, 128, 128, 64);
  protected static final Color COLOR_HELPER = new Color(192, 192, 192);
  // TODO BRIDGE determine reserve, instead of 50 hardcode
  protected static final Scalar RESERVE = RealScalar.of(50);
  // ---
  protected final ConfBase confBase;
  protected final ShowOptions showOptions;
  protected final NavigableMap<Integer, Scalar> navigableMap = new TreeMap<>();
  protected final DateTimeFormatter dateTimeFormatter;
  private Font font = new Font(Font.DIALOG, Font.PLAIN, 12);

  public Axis(ConfBase confBase, ShowOptions showOptions) {
    this.confBase = confBase;
    this.showOptions = showOptions;
    Clip clip = confBase.clip();
    if (clip.min() instanceof DateTime) {
      // TODO BRIDGE 100 is a magic constant that should depend on font, and date formatter
      DateTimeInterval dateTimeInterval = //
          DateTimeInterval.findAboveEquals(clip.width().multiply(Rational.of(100, confBase.width)));
      DateTime startAttempt = dateTimeInterval.floor(clip.min());
      DateTime dateTime = clip.isInside(startAttempt) //
          ? startAttempt
          : dateTimeInterval.plus(startAttempt);
      dateTimeFormatter = showOptions.dateTimeFocus.focus(dateTimeInterval.getSmallestDefined());
      while (clip.isInside(dateTime)) {
        int x_pos = (int) confBase.pixel(dateTime);
        navigableMap.put(x_pos, dateTime);
        dateTime = dateTimeInterval.plus(dateTime);
      }
    } else {
      Ticks.stream(clip, Axis.RESERVE.divide(RealScalar.of(confBase.width))) //
          .forEach(tick -> navigableMap.put((int) confBase.pixel(tick), tick));
      dateTimeFormatter = null;
    }
  }

  /** @param showableConfig
   * @param point
   * @param length
   * @param _g */
  protected final void render(ShowableConfig showableConfig, Point point, Graphics2D graphics) {
    RenderQuality.smoothLine(graphics, false);
    protected_render(showableConfig, point, graphics);
    RenderQuality.smoothLine(graphics, true);
  }

  protected abstract void protected_render(ShowableConfig showableConfig, Point point, Graphics2D graphics);

  public final void setFont(Font font) {
    this.font = font;
  }

  public final Font getFont() {
    return font;
  }

  protected static int interval(FontMetrics fontMetrics) {
    return fontMetrics.getAscent() * 8 / 5;
  }
}
