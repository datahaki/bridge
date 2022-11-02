// code by jph
package ch.alpine.bridge.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.ByteBuffer;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

class VerifiedArrayTest {
  @Test
  void testArray() {
    VerifiedArray verifiedArray = VerifiedArray.of(6);
    assertTrue(verifiedArray.poke(2, new byte[] { 1 }));
    byte[] read = new byte[2];
    assertTrue(verifiedArray.peek(1, read));
    assertEquals(read[0], 0);
    assertEquals(read[1], 1);
    assertFalse(verifiedArray.poke(6, new byte[] { 1 }));
    assertFalse(verifiedArray.poke(5, new byte[] { 1, 0 }));
  }

  @Test
  void testArray2() {
    VerifiedArray verifiedArray = VerifiedArray.of(6);
    assertEquals(verifiedArray.length(), 6);
    assertTrue(verifiedArray.poke(2, new byte[] { 2 }));
    byte[] read = new byte[2];
    assertTrue(verifiedArray.peek(1, read));
    assertEquals(read[0], 0);
    assertEquals(read[1], 2);
    assertFalse(verifiedArray.peek(-1, read));
    assertFalse(verifiedArray.peek(5, read));
    Predicate<ByteBuffer> predicate = e -> false;
    verifiedArray.addPredicate(predicate);
    verifiedArray.addPredicate(e -> false);
    verifiedArray.addPredicate(e -> true);
    verifiedArray.addPredicate(e -> true);
    assertFalse(verifiedArray.poke(2, new byte[] { 3 }));
    verifiedArray.removePredicate(predicate);
    assertFalse(verifiedArray.peek(0, new byte[7]));
  }

  @Test
  void testFails() {
    VerifiedArray.of(0);
    assertThrows(Exception.class, () -> VerifiedArray.of(-1));
  }
}
