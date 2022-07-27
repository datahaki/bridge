// code by jph
package ch.alpine.bridge.gfx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Dot;
import ch.alpine.tensor.ext.Serialization;
import ch.alpine.tensor.lie.r2.CirclePoints;
import ch.alpine.tensor.mat.IdentityMatrix;
import ch.alpine.tensor.sca.Chop;

class GeometricLayerTest {
  @Test
  void testPush() {
    Tensor a = GfxMatrix.translation(Tensors.vector(10, 10));
    GeometricLayer geometricLayer = new GeometricLayer(a);
    Tensor b = GfxMatrix.of(Tensors.vector(2, 3, 4));
    geometricLayer.pushMatrix(b);
    Chop._10.requireClose(Dot.of(a, b), geometricLayer.getMatrix());
    geometricLayer.toPoint2D(Tensors.vector(1, 2));
    geometricLayer.toPoint2D(1, 2);
    geometricLayer.toVector(Tensors.vector(1, 2));
    geometricLayer.toVector(1, 2);
    geometricLayer.toLine2D(Tensors.vector(1, 2));
    geometricLayer.toLine2D(Tensors.vector(1, 2), Tensors.vector(4, 1));
    geometricLayer.toPath2D(CirclePoints.of(10));
    geometricLayer.toPath2D(CirclePoints.of(10), false);
    geometricLayer.toPath2D(CirclePoints.of(10), true);
    geometricLayer.toPath2D(Tensors.empty());
    float model2pixelWidth = geometricLayer.model2pixelWidth(RealScalar.of(3));
    double pixel2modelWidth = geometricLayer.pixel2modelWidth(model2pixelWidth);
    Chop._10.requireClose(RealScalar.of(pixel2modelWidth), RealScalar.of(3));
  }

  @Test
  void testPopFail() {
    GeometricLayer geometricLayer = new GeometricLayer(GfxMatrix.translation(Tensors.vector(0, 0)));
    assertThrows(Exception.class, geometricLayer::popMatrix);
  }

  @Test
  void testSimple() {
    Deque<Integer> deque = new ArrayDeque<>();
    deque.push(2);
    deque.push(4);
    deque.push(9);
    assertEquals((int) deque.peek(), 9);
    deque.pop();
    assertEquals((int) deque.peek(), 4);
    deque.pop();
    assertEquals((int) deque.peek(), 2);
    deque.pop();
    assertEquals(deque.peek(), null);
  }

  @Test
  void testConstruction() {
    Tensor model2pixel = Tensors.fromString("{{1, 2, 3}, {2, -1, 7}, {0, 0, 1}}");
    // Tensor mouseSe2State = Tensors.vector(9, 7, 2);
    GeometricLayer geometricLayer = new GeometricLayer(model2pixel);
    geometricLayer.toPoint2D(Tensors.vector(1, 2));
    assertEquals(geometricLayer.getMatrix(), model2pixel);
    geometricLayer.pushMatrix(IdentityMatrix.of(3));
    assertEquals(geometricLayer.getMatrix(), model2pixel);
    geometricLayer.popMatrix();
    // assertEquals(mouseSe2State, geometricLayer.getMouseSe2State());
    assertThrows(Exception.class, geometricLayer::popMatrix);
  }

  @Test
  void testVector() {
    Tensor model2pixel = Tensors.fromString("{{1, 2, 3}, {2, -1, 7}, {0, 0, 1}}");
    // Tensor mouseSe2State = Tensors.vector(9, 7, 2);
    GeometricLayer geometricLayer = new GeometricLayer(model2pixel);
    Tensor vector = Tensors.vector(9, 20, 1);
    Tensor v1 = geometricLayer.toVector(vector);
    Tensor v2 = geometricLayer.toVector(9, 20);
    Tensor expected = model2pixel.dot(vector).extract(0, 2);
    assertEquals(expected, v1);
    assertEquals(expected, v2);
  }

  @Test
  void testStackFail() {
    GeometricLayer geometricLayer = new GeometricLayer(IdentityMatrix.of(3));
    assertThrows(Exception.class, geometricLayer::popMatrix);
  }

  @Test
  void testSerializableFail() {
    GeometricLayer geometricLayer = new GeometricLayer(IdentityMatrix.of(3));
    assertThrows(Exception.class, () -> Serialization.copy(geometricLayer));
  }
}
