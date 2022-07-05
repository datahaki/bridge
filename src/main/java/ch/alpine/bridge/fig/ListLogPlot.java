// code by jph
package ch.alpine.bridge.fig;

import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.Axis.Type;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListLogPlot.html">ListLogPlot</a> */
public enum ListLogPlot {
  ;
  /** @param visualSet
   * @param joined
   * @return */
  public static JFreeChart of(VisualSet visualSet, boolean joined) {
    visualSet.getAxisY().setType(Type.LOGARITHMIC);
    return ListPlot.of(visualSet, joined);
  }

  /** @param visualSet
   * @return */
  public static JFreeChart of(VisualSet visualSet) {
    return of(visualSet, false);
  }
}
