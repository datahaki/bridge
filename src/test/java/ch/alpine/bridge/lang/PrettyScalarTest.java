// code by jph
package ch.alpine.bridge.lang;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;

class PrettyScalarTest {
  @Test
  void testInteger() {
    System.out.println(PrettyScalar.of(RealScalar.of(123456789)));
    System.out.println(PrettyScalar.of(RealScalar.of(12345678)));
    System.out.println(PrettyScalar.of(RealScalar.of(1234567)));
    System.out.println(PrettyScalar.of(RealScalar.of(-123456789)));
    System.out.println(PrettyScalar.of(RealScalar.of(-12345678)));
    System.out.println(PrettyScalar.of(RealScalar.of(-1234567)));
  }

  @Test
  void testDouble() {
    System.out.println(PrettyScalar.of(RealScalar.of(123456789.123123)));
    System.out.println(PrettyScalar.of(RealScalar.of(12345678.123123)));
    System.out.println(PrettyScalar.of(RealScalar.of(1234567.123123)));
    System.out.println(PrettyScalar.of(RealScalar.of(123456789.25)));
    System.out.println(PrettyScalar.of(RealScalar.of(123456789.25)));
    System.out.println(PrettyScalar.of(RealScalar.of(-123456789.123123)));
    System.out.println(PrettyScalar.of(RealScalar.of(-12345678.123123)));
    System.out.println(PrettyScalar.of(RealScalar.of(-1234567.123123)));
  }
}
