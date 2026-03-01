// code by jph
package ch.alpine.bridge.gfx;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.Serializable;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.jet.AppendOne;
import ch.alpine.tensor.mat.MatrixQ;
import ch.alpine.tensor.mat.re.Det;

/** @see AffineTransform */
public final class AffineFrame2D implements Serializable {
  private final Tensor matrix;

  /** @param matrix of dimensions 3 x 3 */
  public AffineFrame2D(Tensor matrix) {
    MatrixQ.requireSize(matrix, 3, 3);
    this.matrix = matrix;
  }

  public double toX(Tensor xy) {
    return matrix.dot(AppendOne.FUNCTION.apply(xy)).Get(0).number().doubleValue();
  }

  public double toY(Tensor xy) {
    return matrix.dot(AppendOne.FUNCTION.apply(xy)).Get(1).number().doubleValue();
  }

  public Point2D toPoint2D(Tensor xy) {
    Tensor r = matrix.dot(AppendOne.FUNCTION.apply(xy));
    return new Point2D.Double( //
        r.Get(0).number().doubleValue(), //
        r.Get(1).number().doubleValue());
  }

  /** @param px
   * @param py
   * @return vector of length 2 */
  public Tensor toVector(Tensor xy) {
    return matrix.dot(AppendOne.FUNCTION.apply(xy)).extract(0, 2);
  }

  /** @return toPoint2D(0, 0) */
  public Point2D originToPoint2D() {
    Tensor xy = Tensors.of( //
        matrix.Get(0, 2).zero(), //
        matrix.Get(1, 2).zero());
    return toPoint2D(xy);
  }

  /** @param matrix with dimensions 3 x 3
   * @return combined transformation of this and given matrix */
  public AffineFrame2D dot(Tensor matrix) {
    return new AffineFrame2D(this.matrix.dot(matrix));
  }

  /** @return determinant of affine transform, for a standard,
   * right-hand coordinate system, the determinant is negative
   * because pixel coordinates are left handed */
  public Scalar det() {
    return Det.of(matrix);
  }

  /** @return 3 x 3 matrix that represents this transformation */
  public Tensor matrix_copy() {
    return matrix.copy();
  }
}
