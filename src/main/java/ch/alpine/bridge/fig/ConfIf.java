// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;

public interface ConfIf {
  Clip clip();

  double pixel(Scalar x);

  Scalar model(int pixel);

  /** @return this (ConfIncr or ConfDecr) but with ofs == 0 */
  ConfBase pruned();

  /** @param pixel0 old
   * @param pixel1 new
   * @return */
  Scalar shift(int pixel0, int pixel1);
}
