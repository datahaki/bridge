// code by jph
package ch.alpine.bridge.fig;

/** no options is active by default */
public enum PlotOption {
  /** fill primitive */
  FILL,
  /** evaluation of functions restricted to initial domain */
  STRICT,
  /** line vs closed polygon */
  CLOSE,
  /** do not show {@link BarLegend} */
  HIDE_BAR,
}
