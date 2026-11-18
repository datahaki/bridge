// code by jph
package ch.alpine.bridge.fig.grd;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;

public interface ConfBase {
  /** @return width in pixel, always greater equals 2 */
  int length();

  /** @return visible range of model space */
  Clip clip();

  /** @param value in model space
   * @return pixel coordinate of value according to grid */
  double pixel(Scalar value);

  /** @param pixel in the range ofs, ofs + length
   * @return */
  Scalar model(int pixel);

  /** @return this (ConfIncr or ConfDecr) but with ofs == 0 */
  ConfBase pruned();

  /** @param pixel0 old
   * @param pixel1 new
   * @return */
  Scalar shift(int pixel0, int pixel1);
}
