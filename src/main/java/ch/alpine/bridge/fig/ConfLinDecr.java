// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.bridge.fig.plt.ListLinePlot;
import ch.alpine.bridge.fig.plt.ListPlot;
import ch.alpine.bridge.fig.plt.Plot;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;

/** as pixel value ranges from 0 to width,
 * the axis value is decreasing
 * 
 * this is the standard for plots
 * 
 * @see Plot
 * @see ListPlot
 * @see ListLinePlot */
class ConfLinDecr extends ConfLin {
  /** @param ofs
   * @param length is 2 or greater
   * @param clip with width non-zero */
  public ConfLinDecr(int ofs, int length, Clip clip) {
    super(ofs + length - 1, length, clip, Scalar::negate);
  }

  @Override
  public ConfLinDecr pruned() {
    return new ConfLinDecr(0, length(), clip());
  }
}
