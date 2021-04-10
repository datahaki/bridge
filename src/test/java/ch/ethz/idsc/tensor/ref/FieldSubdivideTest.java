// code by jph
package ch.ethz.idsc.tensor.ref;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.alg.Reverse;
import ch.ethz.idsc.tensor.ext.Serialization;
import junit.framework.TestCase;

public class FieldSubdivideTest extends TestCase {
  public void testScalar() throws NoSuchFieldException, SecurityException, ClassNotFoundException, IOException {
    Field field = AnnotatedContainer.class.getField("scalar");
    FieldSubdivide fieldSubdivide = field.getAnnotation(FieldSubdivide.class);
    Serialization.copy(fieldSubdivide);
    Optional<Tensor> optional = TensorReflection.of(fieldSubdivide);
    assertEquals(optional.get(), Reverse.of(Range.of(0, 11)));
  }

  public void testQuantity() throws NoSuchFieldException, SecurityException {
    Field field = AnnotatedContainer.class.getField("quantity");
    FieldSubdivide fieldSubdivide = field.getAnnotation(FieldSubdivide.class);
    Optional<Tensor> optional = TensorReflection.of(fieldSubdivide);
    assertEquals(optional.get(), Tensors.fromString("{1[m], 3/2[m], 2[m], 5/2[m], 3[m]}"));
  }

  public void testTensor() throws NoSuchFieldException, SecurityException {
    Field field = AnnotatedContainer.class.getField("tensor");
    FieldSubdivide fieldSubdivide = field.getAnnotation(FieldSubdivide.class);
    Optional<Tensor> optional = TensorReflection.of(fieldSubdivide);
    assertEquals(optional.get(), Tensors.fromString("{{10, 0}, {11, 0}, {12, 0}, {13, 0}, {14, 0}}"));
  }
}
