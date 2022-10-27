// code by legion
package ch.alpine.bridge.fig;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Objects;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.cal.DateTimeFocus;
import ch.alpine.bridge.lang.Unicode;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.sca.Clip;

public class GridDrawer {
  private final DateTimeFocus dateTimeFocus;
  boolean axesX = true;
  boolean axesY = true;

  public GridDrawer(DateTimeFocus dateTimeFocus) {
    this.dateTimeFocus = Objects.requireNonNull(dateTimeFocus);
  }

  public void render(ShowableConfig showableConfig, Graphics _g) {
    Rectangle rectangle = showableConfig.rectangle;
    Clip xRange = showableConfig.getClip(0);
    Clip yRange = showableConfig.getClip(1);
    // ---
    if (axesX && !Scalars.isZero(xRange.width()))
      new AxisX(dateTimeFocus).render(showableConfig, _g, showableConfig.getClip(0));
    if (axesY && !Scalars.isZero(yRange.width()))
      new AxisY(dateTimeFocus).render(showableConfig, _g, showableConfig.getClip(1));
    // ---
    {
      String unit0 = Unicode.valueOf(QuantityUnit.of(xRange));
      String unit1 = Unicode.valueOf(QuantityUnit.of(yRange));
      if (!unit0.isEmpty() || !unit1.isEmpty()) {
        if (unit0.isEmpty())
          unit0 = "[]";
        if (unit1.isEmpty())
          unit1 = "[]";
        String xLabel = unit0 + "\u2192" + unit1;
        Graphics2D graphics = (Graphics2D) _g.create();
        FontMetrics fontMetrics = graphics.getFontMetrics();
        RenderQuality.setQuality(graphics);
        graphics.setColor(StaticHelper.COLOR_FONT);
        graphics.drawString(xLabel, //
            rectangle.x - fontMetrics.stringWidth(xLabel) - 3 * StaticHelper.GAP, //
            rectangle.y + rectangle.height - 1 + StaticHelper.GAP + fontMetrics.getHeight());
        graphics.dispose();
      }
    }
  }
}
