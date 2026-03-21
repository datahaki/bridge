// code by jph
package ch.alpine.bridge.gfx;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Dot;
import ch.alpine.tensor.mat.IdentityMatrix;
import ch.alpine.tensor.mat.re.Inverse;
import ch.alpine.tensor.qty.Quantity;

class PvmBuilderTest {
  @Test
  void testBackwards() {
    Tensor MODEL2PIXEL_INITIAL = Tensors.matrix(new Number[][] { //
        { 1, 0, 300 }, //
        { 0, -1, 300 }, //
        { 0, 0, 1 }, //
    });
    Tensor digest = PvmBuilder.rhs().setOffset(300, 300).digest();
    assertEquals(MODEL2PIXEL_INITIAL, digest);
  }

  @Test
  void testOriginal() {
    Tensor HANGAR_MODEL2PIXEL = //
        Tensors.fromString("{{7.5, 0, 100}, {0, -7.5, 800}, {0, 0, 1}}");
    Tensor pvm = PvmBuilder.rhs().setOffset(100, 800).setPerPixel(7.5).digest();
    assertEquals(HANGAR_MODEL2PIXEL, pvm);
  }

  @Test
  void testBlub() {
    PvmBuilder pvmBuilder = PvmBuilder.rhs();
    assertEquals(pvmBuilder.digest(), Tensors.fromString("{{1, 0, 0}, {0, -1, 0}, {0, 0, 1}}"));
    pvmBuilder = pvmBuilder.setOffset(300, 400);
    assertEquals(pvmBuilder.digest(), Tensors.fromString("{{1, 0, 300}, {0, -1, 400}, {0, 0, 1}}"));
    pvmBuilder = pvmBuilder.setPerPixel(Quantity.of(20, "m^-1"), Quantity.of(30, "s^-1"));
    assertEquals(pvmBuilder.digest(), //
        Tensors.fromString("{{20[m^-1], 0[s^-1], 300}, {0[m^-1], -30[s^-1], 400}, {0[m^-1], 0[s^-1], 1}}"));
    // geometricComponent.setPerPixel(Quantity.of(60, "m^-1"), Quantity.of(30, "s^-1"));
    // {{60[m^-1], 0[s^-1], 300}, {0[m^-1], -30[s^-1], 300}, {0[m^-1], 0[s^-1], 1}}
    Tensor pvm = pvmBuilder.digest();
    Tensor inv = Inverse.of(pvm);
    assertEquals(inv, Tensors.fromString("{{1/20[m], 0[m], -15[m]}, {0[s], -1/30[s], 40/3[s]}, {0, 0, 1}}"));
    assertEquals(Dot.of(pvm, inv), IdentityMatrix.of(3));
    Tensor dot = inv.dot(pvm);
    assertEquals(dot, //
        Tensors.fromString("{{1, 0[m*s^-1], 0[m]}, {0[m^-1*s], 1, 0[s]}, {0[m^-1], 0[s^-1], 1}}"));
    Tensor pixel = Tensors.vector(100, 200, 1);
    Tensor model = inv.dot(pixel);
    Tensor expec = pvm.dot(model);
    assertEquals(pixel, expec);
  }
}
