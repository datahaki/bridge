// code by jph
package ch.alpine.bridge.fig;

/** all options are active by default */
public enum ShowOption {
  /** draws dark gray box around show area */
  FRAMED,
  /** computes ticks along x axis and draws axis with labels */
  AXIS_X,
  /** computes ticks along y axis and draws axis with labels */
  AXIS_Y,
  /** gridlines requires {@link #AXIS_X} or {@link #AXIS_Y} to take effect */
  GRID,
  /** show unit mapping, e.g. "s -> m/s" */
  UNIT_MAPPING,
  /** Mathematica: "DataReversed"
   * 
   * whether y-axis should be decreasing from top to bottom */
  DECR_Y,
  /** hide legend */
  LEGEND,
}
