// code by jph
package ch.alpine.bridge.fig.grd;

public enum AxisOption {
  /** whether to draw ticks */
  TICK,
  // TODO option to draw grid even without TICK
  /** Careful: in order for grid lines to be drawn, TICK has to be enabled
   * 
   * whether to draw grid lines */
  GRID,
  /** whether is standard plot, otherwise reversed */
  INCR,
}
