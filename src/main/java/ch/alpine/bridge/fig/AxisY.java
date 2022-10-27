// code by legion
package ch.alpine.bridge.fig;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
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
  void protected_render(ShowableConfig showableConfig, Point point, int length, Graphics2D graphics, Clip clip) {
    Rectangle rectangle = showableConfig.rectangle;
    FontMetrics fontMetrics = graphics.getFontMetrics();
    Scalar plotHeight = RealScalar.of(rectangle.height - 1);
    int fontSize = fontMetrics.getAscent();
    Scalar dY = StaticHelper.getDecimalStep(clip.width().divide(plotHeight).multiply(RealScalar.of(fontSize * 2)));
    NavigableMap<Integer, Scalar> navigableMap = new TreeMap<>();
    for ( //
        Scalar yValue = Ceiling.toMultipleOf(dY).apply(clip.min()); //
        Scalars.lessEquals(yValue, clip.max()); //
        yValue = yValue.add(dY)) {
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
        graphics.drawLine(point.x , point.y, point.x , point.y + length - 1);
        for (int piy : navigableMap.keySet())
          graphics.drawLine(point.x  - 2, piy, point.x - 1, piy);
      }
      {
        graphics.setColor(StaticHelper.COLOR_FONT);
        RenderQuality.setQuality(graphics);
        for (Entry<Integer, Scalar> entry : navigableMap.entrySet()) {
          int piy = entry.getKey();
          Scalar yValue = entry.getValue();
          String string = StaticHelper.format(yValue);
          graphics.drawString(string, point.x - fontMetrics.stringWidth(string) - 5, piy + fontSize / 2 - 1);
        }
      }
    }
  }
}
