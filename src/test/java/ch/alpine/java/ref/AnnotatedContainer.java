// code by jph
package ch.alpine.java.ref;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.qty.Quantity;

// DO NOT CHANGE THE SPECIFIED VALUES
// TESTS REQUIRE THE SPECIFIED VALUES
/* package */ class AnnotatedContainer {
  @FieldSubdivide(start = "10", end = "0", intervals = 10)
  public Scalar scalar = RealScalar.ONE;
  @FieldSubdivide(start = "1[m]", end = "3[m]", intervals = 4)
  public Scalar quantity = Quantity.of(1, "m");
  @FieldSubdivide(start = "{10, 0}", end = "{14, 0}", intervals = 4)
  public Tensor tensor = Tensors.vector(11, 0);
  /** fails because min is not a numeric string expression */
  @FieldSubdivide(start = "asd", end = "123", intervals = 2)
  public Scalar nocan;
  /** fails because intervals is not positive */
  @FieldSubdivide(start = "1", end = "2", intervals = 0)
  public Scalar wrong;
  public String text;
  @FieldClip(min = "2", max = "6")
  public Scalar clipped;
  @FieldInteger
  public Scalar integer = RealScalar.of(3);
  @FieldClip(min = "2[kW]", max = "6[kW]")
  public Scalar quantityClipped;
}
