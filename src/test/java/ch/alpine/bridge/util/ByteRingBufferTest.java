// code by jph
package ch.alpine.bridge.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ByteRingBufferTest {
  @Test
  void test() {
    ByteRingBuffer byteRingBuffer = ByteRingBuffer.withSizeInBitResolution(2);
    assertEquals(byteRingBuffer.totalCapacity(), 4);
    byteRingBuffer.discard(0);
    byteRingBuffer.discard(0);
    // assertEquals(byteRingBuffer.available(), 0);
    byteRingBuffer.append(0xf);
    // assertEquals(byteRingBuffer.available(), 1);
    byteRingBuffer.append(0xe);
    // assertEquals(byteRingBuffer.available(), 2);
    assertThrows(Exception.class, () -> byteRingBuffer.discard(3));
    byte[] data = new byte[10];
    assertFalse(byteRingBuffer.peek(data, 3));
    byteRingBuffer.peek(data, 2);
    byteRingBuffer.discard(2);
    assertEquals(data[0], 0xf);
    assertEquals(data[1], 0xe);
    // assertEquals(byteRingBuffer.available(), 0);
    byteRingBuffer.append(0xaf);
    // assertEquals(byteRingBuffer.available(), 1);
    byteRingBuffer.append(0xae);
    // assertEquals(byteRingBuffer.available(), 2);
    byteRingBuffer.append(0xad);
    // assertEquals(byteRingBuffer.available(), 3);
    assertThrows(Exception.class, () -> byteRingBuffer.discard(4));
    byteRingBuffer.peek(data, 3);
    assertEquals(data[0] & 0xff, 0xaf);
    byteRingBuffer.peek(data, 3);
    assertEquals(data[1] & 0xff, 0xae);
    byteRingBuffer.peek(data, 3);
    assertEquals(data[2] & 0xff, 0xad);
    byteRingBuffer.discard(1);
    byteRingBuffer.peek(data, 2);
    assertEquals(data[0] & 0xff, 0xae);
    assertEquals(data[1] & 0xff, 0xad);
    byteRingBuffer.discard(2);
    assertFalse(byteRingBuffer.peek(data, 1));
    // assertEquals(byteRingBuffer.available(), 0);
    byteRingBuffer.append(0xc0);
    // assertEquals(byteRingBuffer.available(), 1);
    byteRingBuffer.append(0xc1);
    // assertEquals(byteRingBuffer.available(), 2);
    byteRingBuffer.append(0xc2);
    // assertEquals(byteRingBuffer.available(), 3);
  }

  @Test
  void testFails() {
    assertThrows(Exception.class, () -> ByteRingBuffer.withSizeInBitResolution(-1));
    assertThrows(Exception.class, () -> ByteRingBuffer.withSizeInBitResolution(0));
    assertThrows(Exception.class, () -> ByteRingBuffer.withSizeInBitResolution(26));
  }

  @Test
  void testOverride() {
    ByteRingBuffer byteRingBuffer = ByteRingBuffer.withSizeInBitResolution(2);
    assertEquals(byteRingBuffer.totalCapacity(), 4);
    byteRingBuffer.append(0);
    byteRingBuffer.append(1);
    byteRingBuffer.append(2);
    byteRingBuffer.append(3);
    byteRingBuffer.append(4);
    // assertEquals(1, byteRingBuffer.available());
  }
}
