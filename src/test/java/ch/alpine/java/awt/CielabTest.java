// code by jph
package ch.alpine.java.awt;

import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.sca.Clips;
import junit.framework.TestCase;

public class CielabTest extends TestCase {
  public void testSimple() {
    Tensor xyz = Tensors.vector(0.3, 0.4, 0.5);
    Tensor lab = Cielab.D65.xyz2lab(xyz);
    lab.map(Clips.absolute(1)::requireInside);
    Tensor bck = Cielab.D65.lab2xyz(lab);
    Tolerance.CHOP.requireClose(xyz, bck);
  }

  public void testVectorFail() {
    AssertFail.of(() -> Cielab.D65.xyz2lab(Tensors.vector(0, 0, 0, 0)));
    AssertFail.of(() -> Cielab.D65.lab2xyz(Tensors.vector(0, 0, 0, 0)));
  }
}
