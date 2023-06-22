// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.Quantity;

class FriendlyFormatTest {
  @Test
  void testSimple() {
    Scalar scalar = FriendlyFormat.of(100000000, "B");
    assertEquals(scalar, Quantity.of(100, "MB"));
  }

  @Test
  void testFractional() {
    assertEquals(FriendlyFormat.toHighSchoolString(Scalars.fromString("2/3")), "2/3");
    assertEquals(FriendlyFormat.toHighSchoolString(Scalars.fromString("4/3")), "1+1/3");
    assertEquals(FriendlyFormat.toHighSchoolString(Scalars.fromString("5")), "5");
  }

  @Test
  void testBytes() {
    String string = FriendlyFormat.of(new byte[] { 2, 3, (byte) 255 });
    assertEquals(string, "[02 03 ff]");
  }
}
