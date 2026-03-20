// code by jph
package ch.alpine.bridge.fig;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

import ch.alpine.bridge.cal.DateTimeFocus;
import ch.alpine.bridge.cal.ISO8601DateTimeFocus;

class ShowOptions implements Serializable {
  private final Set<ShowOption> set = EnumSet.allOf(ShowOption.class);
  String plotLabel = "";
  public DateTimeFocus dateTimeFocus = ISO8601DateTimeFocus.INSTANCE;

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
