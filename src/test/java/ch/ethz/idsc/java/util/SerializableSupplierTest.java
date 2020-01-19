// code by jph
package ch.ethz.idsc.java.util;

import junit.framework.TestCase;

public class SerializableSupplierTest extends TestCase {
  public void testDummy() {
  }
  // public void testSimple() throws ClassNotFoundException, IOException {
  // SerializableSupplier<Tensor> serializableSupplier = () -> Tensors.empty();
  // Supplier<Tensor> supplier = Serialization.copy(serializableSupplier);
  // assertEquals(supplier.get(), Tensors.empty());
  // }
  //
  // public void testOptional() throws ClassNotFoundException, IOException {
  // SerializableSupplier<Optional<Tensor>> serializableSupplier = () -> Optional.of(RealScalar.ONE);
  // Supplier<Optional<Tensor>> supplier = Serialization.copy(serializableSupplier);
  // assertEquals(supplier.get().get(), RealScalar.ONE);
  // }
}
