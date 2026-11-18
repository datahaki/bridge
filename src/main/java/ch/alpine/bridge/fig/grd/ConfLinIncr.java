// code by jph
package ch.alpine.bridge.fig.grd;

import ch.alpine.bridge.fig.plt.ImagePlot;
import ch.alpine.tensor.io.MathematicaFormat;
import ch.alpine.tensor.sca.Clip;

/** as pixel value ranges from 0 to width,
 * the axis value is increasing
 * 
 * this axis is used to indicate pixel coordinates of images
 * 
 * @see ImagePlot */
public class ConfLinIncr extends ConfLin {
  /** @param ofs
   * @param length is 2 or greater
   * @param clip with width non-zero */
  public ConfLinIncr(int ofs, int length, Clip clip) {
    super(ofs, length, clip, s -> s);
  }

  @Override
  public ConfLinIncr pruned() {
    return new ConfLinIncr(0, length(), clip());
  }

  @Override
  public String toString() {
    return MathematicaFormat.concise("ConfLinIncr", ofs, length, clip);
  }
}
