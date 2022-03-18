// code by jph
package ch.alpine.java.awt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.sca.Clips;

public class CielabTest {
  @Test
  public void testSimple() {
    Tensor xyz = Tensors.vector(0.3, 0.4, 0.5);
    Tensor lab = Cielab.D65.xyz2lab(xyz);
    lab.map(Clips.absolute(1)::requireInside);
    Tensor bck = Cielab.D65.lab2xyz(lab);
    Tolerance.CHOP.requireClose(xyz, bck);
  }

  @Test
  public void testAlpha() {
    Color c1 = new Color(255, 255, 255, 0);
    Color c2 = new Color(255, 255, 255, 255);
    Color split = Cielab.D50.split(c1, c2, RealScalar.of(0.3));
    assertEquals(split.getAlpha(), 76);
  }

  @Test
  public void testVectorFail() {
    AssertFail.of(() -> Cielab.D65.xyz2lab(Tensors.vector(0, 0, 0, 0)));
    AssertFail.of(() -> Cielab.D65.lab2xyz(Tensors.vector(0, 0, 0, 0)));
  }
}
