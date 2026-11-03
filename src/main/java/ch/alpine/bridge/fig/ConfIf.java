// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;

public interface ConfIf {
  Clip clip();

  /** @return in pixel */
  int length();

  /** @param x
   * @return pixel coordinate of x */
  double pixel(Scalar x);

  Scalar model(int pixel);

  /** @param pixel0 old
   * @param pixel1 new
   * @return */
  Scalar shift(int pixel0, int pixel1);

  /** @return this (ConfIncr or ConfDecr) but with ofs == 0 */
  ConfIf pruned();
}
