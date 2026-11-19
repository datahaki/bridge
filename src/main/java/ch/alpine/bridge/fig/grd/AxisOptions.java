// code by jph
package ch.alpine.bridge.fig.grd;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

import ch.alpine.bridge.cal.DateTimeFocus;
import ch.alpine.bridge.cal.ISO8601DateTimeFocus;
import ch.alpine.tensor.sca.Clip;

public class AxisOptions implements Serializable {
  private final Set<AxisOption> set = EnumSet.allOf(AxisOption.class);
  public AxisMap axisMap = AxisMaps.LIN;
  public DateTimeFocus dateTimeFocus = ISO8601DateTimeFocus.INSTANCE;

  public void set(AxisOption axisOption, boolean status) {
    if (status)
      set.add(axisOption);
    else
      set.remove(axisOption);
  }

  public boolean contains(AxisOption axisOption) {
    return set.contains(axisOption);
  }

  public AxisConfig config(boolean flip, int ofs, int length, Clip clip) {
    return AxisConfig.create(ofs, length, axisMap, clip, flip ^ contains(AxisOption.INCR));
  }
}
