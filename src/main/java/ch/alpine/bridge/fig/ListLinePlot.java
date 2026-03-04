// code by jph
package ch.alpine.bridge.fig;

import java.util.EnumSet;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Transpose;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListLinePlot.html">ListLinePlot</a> */
public enum ListLinePlot {
  ;
  /** @param points of the form {{x1, y1}, {x2, y2}, ..., {xn, yn}}
   * The special case when points == {} is also allowed.
   * @return instance of the visual row, that was added to this visual set
   * @throws Exception if not all entries in points are vectors of length 2 */
  public static Showable of(Tensor points) {
    return new PolygonPlot(points, EnumSet.noneOf(PlotOption.class));
  }

  /** @param domain {x1, x2, ..., xn}
   * @param values {y1, y2, ..., yn}
   * @return */
  public static Showable of(Tensor domain, Tensor tensor) {
    return of(Transpose.of(Tensors.of(domain, tensor)));
  }
}
