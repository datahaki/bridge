// code by jph
package ch.alpine.bridge.fig;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Flatten;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Round;
import ch.alpine.tensor.sca.pow.Sqrt;

public enum GraphicsGrid {
  ;
  public static List<Rectangle> rectangles(int n, Rectangle rectangle) {
    Point point = rectangle.getLocation();
    Dimension size = rectangle.getSize();
    Scalar sqrt = Sqrt.FUNCTION.apply(RealScalar.of(n));
    int cols = Round.intValueExact(sqrt);
    int rows = Ceiling.intValueExact(sqrt);
    Dimension dimension = new Dimension(size.width / cols, size.height / rows);
    Tensor matrix = Tensors.matrix((i, j) -> Tensors.vector(i, j), rows, cols);
    return Flatten.of(matrix, 1).stream().map(ij -> new Rectangle(new Point( //
        point.x + ij.Get(1).number().intValue() * dimension.width, //
        point.y + ij.Get(0).number().intValue() * dimension.height), dimension)).toList();
  }

  public static List<Rectangle> rectangles(int n, Dimension dimension) {
    return rectangles(n, new Rectangle(new Point(), dimension));
  }

  // TODO name is bad
  public static List<Rectangle> rectanglesCol(int n, Rectangle rectangle) {
    List<Rectangle> list = new ArrayList<>(n);
    Dimension dimension = rectangle.getSize();
    dimension.height /= n;
    for (int i = 0; i < n; ++i)
      list.add(new Rectangle(new Point(rectangle.x, rectangle.y + (i * dimension.height)), dimension));
    return list;
  }
}
