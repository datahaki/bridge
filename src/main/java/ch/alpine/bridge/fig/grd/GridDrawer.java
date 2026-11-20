// code by legion
package ch.alpine.bridge.fig.grd;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import ch.alpine.bridge.fig.ShowOption;
import ch.alpine.bridge.fig.ShowOptions;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.bridge.lang.UnicodeString;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.sca.Clip;

public record GridDrawer(ShowOptions showOptions) {
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    Rectangle rectangle = showableConfig.rectangle();
    Clip xRange = showableConfig.confX().range_clip();
    Clip yRange = showableConfig.confY().range_clip();
    // ---
    final int piy = rectangle.y + rectangle.height - 1 + ShowOptions.GAP;
    if (showOptions.axisOptionsX.contains(AxisOption.TICK) && xRange.isNonDegenerate())
      new AxisX(showOptions.axisOptionsX).render( //
          showableConfig, //
          new Point(rectangle.x, piy), //
          graphics);
    if (showOptions.axisOptionsY.contains(AxisOption.TICK) && yRange.isNonDegenerate())
      new AxisYL(showOptions.axisOptionsY).render( //
          showableConfig, //
          new Point(rectangle.x - ShowOptions.GAP, rectangle.y), //
          graphics);
    // ---
    {
    }
    if (showOptions.contains(ShowOption.UNIT_MAPPING)) {
      String unit0 = UnicodeString.of(QuantityUnit.of(xRange));
      String unit1 = UnicodeString.of(QuantityUnit.of(yRange));
      if (!unit0.isEmpty() || !unit1.isEmpty()) {
        if (unit0.isEmpty())
          unit0 = "[]";
        if (unit1.isEmpty())
          unit1 = "[]";
        String xLabel = unit0 + "\u2192" + unit1;
        FontMetrics fontMetrics = graphics.getFontMetrics();
        graphics.setColor(ShowOptions.COLOR_FONT);
        graphics.drawString(xLabel, //
            rectangle.x - fontMetrics.stringWidth(xLabel) - 3 * ShowOptions.GAP, //
            piy + ShowOptions.TICK + fontMetrics.getAscent());
      }
    }
  }
}
