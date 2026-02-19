// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.num.Pi;
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
    assertEquals(FriendlyFormat.toHighSchoolString(Pi.VALUE), Pi.VALUE.toString());
  }

  @Test
  void testBytes() {
    String string = FriendlyFormat.of(new byte[] { 2, 3, (byte) 255 });
    assertEquals(string, "[02 03 ff]");
  }

  @Test
  void testFilename() {
    String string = FriendlyFormat.sanitize("s ls \u3000 323 \\ dff& / :{}.csv _ ");
    assertEquals(string, "s ls 　 323 _ dff& _ _{}.csv _");
  }

  @Test
  void testAmps() {
    String string = FriendlyFormat.convertChars("asf<>'");
    assertEquals(string, "asf&lt;&gt;&apos;");
    string = FriendlyFormat.convertAmps(string);
    assertEquals(string, "asf<>'");
  }

  @Test
  void testSanitize() {
    String string = FriendlyFormat.sanitize(" .a12sd  //?* . txt  ");
    assertEquals(string, " .a12sd  ____ . txt");
  }

  @Test
  void testCamel() {
    String string = FriendlyFormat.toCamelCase("GRAND_PIANO");
    assertEquals(string, "GrandPiano");
  }

  @Test
  void testDefaultTitle() {
    String string = FriendlyFormat.defaultTitle(getClass());
    assertEquals(string, "Friendly Format Test");
  }
}
