// code by jph
package ch.alpine.bridge.fig;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

import ch.alpine.bridge.fig.grd.AxisOptions;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public class ShowOptions implements Serializable {
  public static final Color COLOR_FONT = Color.DARK_GRAY;
  // ---
  public static final Stroke STROKE_SOLID = new BasicStroke();
  public static final int GAP = 5;
  public static final int TICK = 3;
  // ---
  private final Set<ShowOption> set = EnumSet.allOf(ShowOption.class);
  public final AxisOptions axisOptionsX = new AxisOptions();
  public final AxisOptions axisOptionsY = new AxisOptions();
  Font font = new Font(Font.DIALOG, Font.PLAIN, 12);
  String showLabel = "";

  public ShowableConfig config(Rectangle rectangle, CoordinateBoundingBox cbb) {
    return new ShowableConfig(rectangle, cbb, //
        axisOptionsX.config(false, rectangle.x, rectangle.width, cbb.clip(0)), //
        axisOptionsY.config(true, rectangle.y, rectangle.height, cbb.clip(1)));
  }

  public void set(ShowOption showOption, boolean status) {
    if (status)
      set.add(showOption);
    else
      set.remove(showOption);
  }

  public boolean contains(ShowOption showOption) {
    return set.contains(showOption);
  }
}
