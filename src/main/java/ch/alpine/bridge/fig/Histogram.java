// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.util.function.Function;

import ch.alpine.tensor.Scalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Histogram.html">Histogram</a> */
public enum Histogram {
  ;
  /** @param visualSet
   * @return */
  public static Showable of(Show visualSet) {
    return of(visualSet, false);
  }

  /** @param visualSet
   * @param stacked
   * @return
   * @see StackedHistogram */
  /* package */ static Showable of(Show visualSet, boolean stacked) {
    // return JFreeChartFactory.barChart(visualSet, stacked, Unicode::valueOf);
    return null;
  }

  /** @param visualSet
   * @param stacked
   * @param naming for coordinates on x-axis
   * @return */
  public static Showable of(Show visualSet, boolean stacked, Function<Scalar, String> naming) {
    return null; // JFreeChartFactory.barChart(visualSet, stacked, naming);
  }
}
