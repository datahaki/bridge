// code by jph
package ch.alpine.bridge.fig;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

import ch.alpine.bridge.cal.DateTimeFocus;
import ch.alpine.bridge.cal.ISO8601DateTimeFocus;

class AxisOptions implements Serializable {
  private final Set<AxisOption> set = EnumSet.noneOf(AxisOption.class);
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
}
