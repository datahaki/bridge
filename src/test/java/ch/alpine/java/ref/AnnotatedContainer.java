// code by jph
package ch.alpine.java.ref;

import ch.alpine.java.ref.ann.FieldClip;
import ch.alpine.java.ref.ann.FieldInteger;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.qty.Quantity;

// DO NOT CHANGE THE SPECIFIED VALUES
// TESTS REQUIRE THE SPECIFIED VALUES
public class AnnotatedContainer {
  public Scalar scalar = RealScalar.ONE;
  public Scalar quantity = Quantity.of(1, "m");
  public Tensor tensor = Tensors.vector(11, 0);
  /** fails because min is not a numeric string expression */
  public Scalar nocan;
  /** fails because intervals is not positive */
  public Scalar wrong;
  public String text;
  @FieldClip(min = "2", max = "6")
  public Scalar clipped;
  @FieldInteger
  public Scalar integer = RealScalar.of(3);
  @FieldClip(min = "2[kW]", max = "6[kW]")
  public Scalar quantityClipped;
}
