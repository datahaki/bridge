// code by jph
package ch.alpine.bridge.awt;

import java.awt.Dimension;
import java.awt.Point;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

public enum AwtUtil {
  ;
  public static Point center(Dimension dimension) {
    return new Point(dimension.width / 2, dimension.height / 2);
  }

  public static Tensor toTensor(Point point) {
    return Tensors.vector(point.x, point.y);
  }
}
