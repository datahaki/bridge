// code by legion
package ch.alpine.bridge.fig;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.lang.UnicodeString;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.sca.Clip;

public class GridDrawer {
  private final ShowOptions showOptions;

  public GridDrawer(ShowOptions showOptions) {
    this.showOptions = showOptions;
  }

  public GridDrawer() {
    this(new ShowOptions());
  }

  public void render(ShowableConfig showableConfig, Graphics _g) {
    Rectangle rectangle = showableConfig.rectangle;
    Clip xRange = showableConfig.getClip(0);
    Clip yRange = showableConfig.getClip(1);
    // ---
    if (showOptions.contains(ShowOption.AXIS_X) && !Scalars.isZero(xRange.width()))
      new AxisX(showOptions).render( //
          showableConfig, //
          new Point(rectangle.x, rectangle.y + rectangle.height - 1 + StaticHelper.GAP), //
          rectangle.width, _g, showableConfig.getClip(0));
    if (showOptions.contains(ShowOption.AXIS_Y) && !Scalars.isZero(yRange.width()))
      new AxisY(showOptions).render( //
          showableConfig, //
          new Point(rectangle.x - StaticHelper.GAP, rectangle.y), //
          rectangle.height, _g, showableConfig.getClip(1));
    // ---
    {
      String unit0 = UnicodeString.of(QuantityUnit.of(xRange));
      String unit1 = UnicodeString.of(QuantityUnit.of(yRange));
      if (!unit0.isEmpty() || !unit1.isEmpty()) {
        if (unit0.isEmpty())
          unit0 = "[]";
        if (unit1.isEmpty())
          unit1 = "[]";
        String xLabel = unit0 + "\u2192" + unit1;
        Graphics2D graphics = (Graphics2D) _g.create();
        RenderQuality.setQuality(graphics);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        graphics.setColor(StaticHelper.COLOR_FONT);
        graphics.drawString(xLabel, //
            rectangle.x - fontMetrics.stringWidth(xLabel) - 3 * StaticHelper.GAP, //
            rectangle.y + rectangle.height - 1 + StaticHelper.GAP + fontMetrics.getHeight());
        graphics.dispose();
      }
    }
  }
}
