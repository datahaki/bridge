// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.img.ColorDataGradient;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
public enum ArrayPlot {
  ;
  /** function emulates ArrayPlot[matrix] in Mathematica
   * 
   * Other that in Mathematica, the axes are drawn with ticks.
   * 
   * Hint:
   * use {@link JFreeChart#setTitle(String)} to define plot label
   * 
   * @param matrix
   * @return */
  public static JFreeChart of(Tensor matrix) {
    return of(VisualImage.of(matrix));
  }

  /** @param visualImage
   * @return */
  public static JFreeChart of(VisualImage visualImage) {
    return null;
  }

  public static JFreeChart of(Tensor matrix, ColorDataGradient colorDataGradient) {
    return of(VisualImage.of(matrix, colorDataGradient));
  }
}
