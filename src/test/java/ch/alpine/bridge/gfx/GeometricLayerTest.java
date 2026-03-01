// code by jph
package ch.alpine.bridge.gfx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.alg.Dot;
import ch.alpine.tensor.ext.Serialization;
import ch.alpine.tensor.jet.AppendOne;
import ch.alpine.tensor.lie.rot.CirclePoints;
import ch.alpine.tensor.mat.IdentityMatrix;
import ch.alpine.tensor.sca.Chop;
import ch.alpine.tensor.sca.tri.Cos;
import ch.alpine.tensor.sca.tri.Sin;

class GeometricLayerTest {
  public static Tensor gfxMatrix_of(Tensor xya) {
    Scalar angle = xya.Get(2);
    Scalar cos = Cos.FUNCTION.apply(angle);
    Scalar sin = Sin.FUNCTION.apply(angle);
    return Tensors.matrix(new Scalar[][] { //
        { cos, sin.negate(), xya.Get(0) }, //
        { sin, cos /*----*/, xya.Get(1) }, //
        { RealScalar.ZERO, RealScalar.ZERO, RealScalar.ONE }, //
    });
  }

  public static Tensor gfxMatrix_translation(Tensor xy) {
    return Tensors.matrix(new Scalar[][] { //
        { RealScalar.ONE, RealScalar.ZERO, xy.Get(0) }, //
        { RealScalar.ZERO, RealScalar.ONE, xy.Get(1) }, //
        { RealScalar.ZERO, RealScalar.ZERO, RealScalar.ONE }, //
    });
  }

  @Test
  void testSimple345() {
    Tensor m1 = gfxMatrix_of(Tensors.vector(1, 2, 3));
    Tensor m2 = gfxMatrix_of(Tensors.vector(-.3, 0.2, .4));
    AffineFrame2D af2 = new AffineFrame2D(m1);
    AffineFrame2D af3 = af2.dot(m2);
    assertEquals(af3.matrix_copy(), m1.dot(m2));
    Point2D point2d = af3.originToPoint2D();
    Point2D actual = new Point2D.Double(1.2687737473681602, 1.7596654982619508);
    assertTrue(point2d.distance(actual) < 1e-9);
    assertTrue(point2d.distance(af3.toPoint2D(Array.zeros(2))) < 1e-9);
  }

  @Test
  void testPoint() {
    Tensor m1 = gfxMatrix_of(Tensors.vector(1, 2, 3));
    AffineFrame2D af2 = new AffineFrame2D(m1);
    Tensor v = Tensors.vector(-.3, -.4);
    Point2D p = af2.toPoint2D(v);
    Tensor q = m1.dot(AppendOne.FUNCTION.apply(v));
    assertEquals(p.getX(), q.Get(0).number().doubleValue());
    assertEquals(p.getY(), q.Get(1).number().doubleValue());
  }

  @Test
  void testPush() {
    Tensor a = gfxMatrix_translation(Tensors.vector(10, 10));
    GeometricLayer geometricLayer = new GeometricLayer(a);
    Tensor b = gfxMatrix_of(Tensors.vector(2, 3, 4));
    geometricLayer.pushMatrix(b);
    Chop._10.requireClose(Dot.of(a, b), geometricLayer.getMatrix());
    geometricLayer.toPoint2D(Tensors.vector(1, 2));
    geometricLayer.toVector(Tensors.vector(1, 2));
    geometricLayer.toLine2D(Tensors.vector(1, 2));
    geometricLayer.toLine2D(Tensors.vector(1, 2), Tensors.vector(4, 1));
    geometricLayer.toPath2D(CirclePoints.of(10));
    geometricLayer.toPath2D(CirclePoints.of(10), false);
    geometricLayer.toPath2D(CirclePoints.of(10), true);
    geometricLayer.toPath2D(Tensors.empty());
    Scalar in = RealScalar.of(3);
    Scalar model2pixelWidth = geometricLayer.model2pixelWidth(in);
    Scalar pixel2modelWidth = geometricLayer.pixel2modelWidth(model2pixelWidth);
    Chop._10.requireClose(pixel2modelWidth, in);
  }

  @Test
  void testPopFail() {
    GeometricLayer geometricLayer = new GeometricLayer(gfxMatrix_translation(Tensors.vector(0, 0)));
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
    Tensor v2 = geometricLayer.toVector(Tensors.vector(9, 20));
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
