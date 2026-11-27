// code by jph
package ch.alpine.bridge.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Dimension;
import java.util.List;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Dimensions;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Round;

class MeshgridTest {
  @Test
  void testSimple() {
    CoordinateBoundingBox cbb = CoordinateBoundingBox.of(Clips.absolute(2), Clips.absolute(3));
    Tensor tensor = Meshgrid.of(cbb, 20).image(t -> t);
    List<Integer> list = Dimensions.of(tensor);
    assertEquals(list, List.of(20, 20, 2));
  }

  @Test
  void testRect() {
    CoordinateBoundingBox cbb = CoordinateBoundingBox.of(Clips.positive(20), Clips.positive(30));
    Meshgrid meshgrid = new Meshgrid(cbb, 2, 4);
    assertEquals(meshgrid.px_incr(), Tensors.vector(5, 15));
    assertEquals(meshgrid.py_decr(), Tensors.vector(26.25, 18.75, 11.25, 3.75));
    Tensor imx = meshgrid.image((x, _) -> x).maps(Round.FUNCTION);
    List<Integer> list = Dimensions.of(imx);
    assertEquals(list, List.of(4, 2));
  }

  @Test
  void testDim() {
    CoordinateBoundingBox cbb = CoordinateBoundingBox.of(Clips.positive(20), Clips.positive(30));
    Meshgrid meshgrid = Meshgrid.of(cbb, new Dimension(2, 4));
    assertEquals(meshgrid.px_incr(), Tensors.vector(5, 15));
    assertEquals(meshgrid.py_decr(), Tensors.vector(26.25, 18.75, 11.25, 3.75));
    Tensor imx = meshgrid.image((x, _) -> x).maps(Round.FUNCTION);
    List<Integer> list = Dimensions.of(imx);
    assertEquals(list, List.of(4, 2));
  }
}
