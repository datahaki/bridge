// code by jph
package ch.alpine.bridge.gfx;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;

/** GeometricLayer transforms from model to pixel coordinates
 * 
 * see RenderInterface */
public final class GeometricLayer {
  private final Deque<AffineFrame2D> deque = new ArrayDeque<>();

  /** @param model2pixel matrix of dimension 3x3 that becomes first element on matrix stack */
  public GeometricLayer(Tensor model2pixel) {
    deque.push(new AffineFrame2D(model2pixel.copy()));
  }

  /** only the first 2 entries of x are taken into account
   * 
   * @param vector of the form {px, py, ...}
   * @return */
  public Point2D toPoint2D(Tensor vector) {
    return deque.peek().toPoint2D(vector.extract(0, 2));
  }

  /** @param vector of the form {px, py, ...}
   * @return vector of length 2 */
  public Tensor toVector(Tensor vector) {
    return deque.peek().toVector(vector.extract(0, 2));
  }

  /** inspired by opengl
   * 
   * @param matrix 3x3 */
  public void pushMatrix(Tensor matrix) {
    deque.push(deque.peek().dot(matrix));
  }

  /** inspired by opengl
   * 
   * @throws Exception without a corresponding call to {@link #pushMatrix(Tensor)} */
  public void popMatrix() {
    if (deque.size() == 1)
      throw new IllegalStateException();
    deque.pop();
  }

  /** @return current model2pixel matrix */
  public Tensor getMatrix() {
    return deque.peek().matrix_copy();
  }

  /** @param p
   * @param q
   * @return line that connects p and q */
  public Line2D toLine2D(Tensor p, Tensor q) {
    return new Line2D.Double( //
        toPoint2D(p), //
        toPoint2D(q));
  }

  /** @param p
   * @return line that connects the origin with p */
  public Line2D toLine2D(Tensor p) {
    return new Line2D.Double( //
        deque.peek().originToPoint2D(), //
        toPoint2D(p));
  }

  /** @param polygon
   * @return path that is not closed */
  public Path2D toPath2D(Tensor polygon) {
    Path2D path2d = new Path2D.Double(PathIterator.WIND_NON_ZERO, polygon.length());
    toPath2D(path2d, polygon);
    return path2d;
  }

  /** @param path2d to which moveTo and lineTo directives are written
   * @param polygon */
  public void toPath2D(Path2D path2d, Tensor polygon) {
    if (Tensors.nonEmpty(polygon)) {
      Point2D point2d = toPoint2D(polygon.get(0));
      path2d.moveTo(point2d.getX(), point2d.getY());
    }
    polygon.stream() //
        .skip(1) // first coordinate already used in moveTo
        .map(this::toPoint2D) //
        .forEach(point2d -> path2d.lineTo(point2d.getX(), point2d.getY()));
  }

  /** @param polygon
   * @param close
   * @return path that is closed if given parameter close is true */
  public Path2D toPath2D(Tensor polygon, boolean close) {
    Path2D path2d = toPath2D(polygon);
    if (close)
      path2d.closePath();
    return path2d;
  }

  public boolean isAxisAligned() {
    return deque.peek().isAxisAligned();
  }

  public Optional<Rectangle> toRectangle(CoordinateBoundingBox cbb) {
    Point2D min = toPoint2D(cbb.min());
    Point2D max = toPoint2D(cbb.max());
    return isAxisAligned() //
        ? Optional.of(new Rectangle( //
            (int) min.getX(), //
            (int) max.getY(), //
            (int) (max.getX() - min.getX()), //
            (int) (min.getY() - max.getY())))
        : Optional.empty();
  }

  /** transforms point in pixel space to coordinates of model space
   * 
   * @param point
   * @return tensor of length 2 */
  public Optional<CoordinateBoundingBox> fromRectangle(Rectangle rectangle) {
    if (isAxisAligned()) {
      AffineFrame2D affineFrame2D = deque.peek();
      return Optional.of(CoordinateBounds.of(Tensors.of( //
          affineFrame2D.toModel(rectangle.x, rectangle.y), //
          affineFrame2D.toModel(rectangle.x + rectangle.width, rectangle.y + rectangle.height))));
    }
    return Optional.empty();
  }

  /** function allows to render lines with width defined in model coordinates
   * <pre>
   * new BasicStroke(geometricLayer.model2pixelWidth(0.1))
   * </pre>
   * 
   * @param modelWidth
   * @return non-negative value */
  public Scalar model2pixelFactor(Scalar modelWidth) {
    return deque.peek().sqrtDet().multiply(modelWidth);
  }

  public Scalar pixel2modelFactor(Scalar pixelWidth) {
    return pixelWidth.divide(deque.peek().sqrtDet());
  }
}
