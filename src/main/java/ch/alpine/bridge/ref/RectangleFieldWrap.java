// code by jph
package ch.alpine.bridge.ref;

import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.util.Objects;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.VectorQ;

/* package */ class RectangleFieldWrap extends SelectableFieldWrap {
  public RectangleFieldWrap(Field field) {
    super(field);
  }

  @Override // from FieldWrap
  public Rectangle toValue(String string) {
    Objects.requireNonNull(string);
    try {
      Tensor tensor = Tensors.fromString(string);
      if (VectorQ.ofLength(tensor, 4)) {
        int[] array = tensor.stream() //
            .map(Scalar.class::cast) //
            .mapToInt(Scalars::intValueExact).toArray();
        return new Rectangle( //
            array[0], //
            array[1], //
            array[2], //
            array[3]);
      }
    } catch (Exception exception) {
      // ---
    }
    return null;
  }

  @Override // from FieldWrap
  public String toString(Object value) {
    Rectangle rectangle = (Rectangle) value;
    return Tensors.vectorInt( //
        rectangle.x, //
        rectangle.y, //
        rectangle.width, //
        rectangle.height).toString();
  }
}
