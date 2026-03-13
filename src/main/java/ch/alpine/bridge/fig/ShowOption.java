// code by jph
package ch.alpine.bridge.fig;

public enum ShowOption {
  /** draws dark gray box around show area */
  FRAMED,
  /** computes ticks along x axis and draws axis with labels */
  AXIS_X,
  /** computes ticks along y axis and draws axis with labels */
  AXIS_Y,
  /** gridlines requires {@link #AXIS_X} or {@link #AXIS_Y} to take effect */
  GRID
}
