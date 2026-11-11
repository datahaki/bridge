// code by jph
package ch.alpine.bridge.fig;

import java.awt.Font;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

import ch.alpine.bridge.cal.DateTimeFocus;
import ch.alpine.bridge.cal.ISO8601DateTimeFocus;
import ch.alpine.bridge.geo.TilePixel;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

class ShowOptions implements Serializable {
  private final Set<ShowOption> set = EnumSet.allOf(ShowOption.class);
  Font font = new Font(Font.DIALOG, Font.PLAIN, 12);
  String showLabel = "";
  public DateTimeFocus dateTimeFocus = ISO8601DateTimeFocus.INSTANCE;

  public ShowableConfig config(Rectangle rectangle, CoordinateBoundingBox cbb) {
    return contains(ShowOption.DECR_Y) //
        ? ShowableConfig.yDecr(rectangle, cbb)
        : ShowableConfig.yIncr(rectangle, cbb);
  }

  public ShowableConfig geo(Rectangle rectangle, CoordinateBoundingBox cbb) {
    TilePixel origin = TilePixel.geo(rectangle, cbb);
    return ShowableConfig.geo(rectangle, cbb, origin);
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

  public AxisOptions compileAxisX() {
    AxisOptions axisOptions = new AxisOptions();
    axisOptions.set(AxisOption.GRID, contains(ShowOption.GRID));
    axisOptions.set(AxisOption.TICK, contains(ShowOption.AXIS_X));
    return axisOptions;
  }

  public AxisOptions compileAxisY() {
    AxisOptions axisOptions = new AxisOptions();
    axisOptions.set(AxisOption.GRID, contains(ShowOption.GRID));
    axisOptions.set(AxisOption.TICK, contains(ShowOption.AXIS_Y));
    return axisOptions;
  }
}
