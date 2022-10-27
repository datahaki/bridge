// code by legion
package ch.alpine.bridge.fig;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.cal.DateTimeFocus;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Clip;

class AxisY extends Axis {
  public AxisY(DateTimeFocus dateTimeFocus) {
    super(dateTimeFocus);
  }

  /** draw lines and numbers like this: _________________ */
  @Override
  void protected_render(ShowableConfig showableConfig, Graphics2D graphics) {
    Rectangle rectangle = showableConfig.rectangle;
    Clip yRange = showableConfig.getClip(1);
    Scalar plotHeight = RealScalar.of(rectangle.height - 1);
    FontMetrics fontMetrics = graphics.getFontMetrics();
    int fontSize = fontMetrics.getAscent();
    Scalar dY = StaticHelper.getDecimalStep(yRange.width().divide(plotHeight).multiply(RealScalar.of(fontSize * 2)));
    NavigableMap<Integer, Scalar> navigableMap = new TreeMap<>();
    for (Scalar yValue = Ceiling.toMultipleOf(dY).apply(yRange.min()); Scalars.lessEquals(yValue, yRange.max()); yValue = yValue.add(dY)) {
      int y_pos = (int) showableConfig.y_pos(yValue);
      navigableMap.put(y_pos, yValue);
    }
    if (gridLines) {
      graphics.setStroke(STROKE_GRIDLINES);
      graphics.setColor(COLOR_GRIDLINES);
      for (int piy : navigableMap.keySet())
        graphics.drawLine(rectangle.x, piy, rectangle.x + rectangle.width - 1, piy);
    }
    if (ticks) {
      {
        graphics.setStroke(StaticHelper.STROKE_SOLID);
        graphics.setColor(COLOR_HELPER);
        graphics.drawLine(rectangle.x - StaticHelper.GAP, rectangle.y, rectangle.x - StaticHelper.GAP, rectangle.y + rectangle.height - 1);
        for (int piy : navigableMap.keySet())
          graphics.drawLine(rectangle.x - StaticHelper.GAP - 2, piy, rectangle.x - StaticHelper.GAP - 1, piy);
      }
      {
        graphics.setColor(StaticHelper.COLOR_FONT);
        RenderQuality.setQuality(graphics);
        for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
          int piy = entry.getKey();
          Scalar yValue = entry.getValue();
          String string = StaticHelper.format(yValue);
          graphics.drawString(string, rectangle.x - fontMetrics.stringWidth(string) - StaticHelper.GAP - 5, piy + fontSize / 2 - 1);
        }
      }
    }
  }
}
