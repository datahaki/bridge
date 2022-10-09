// code by jph
package ch.alpine.bridge.util;

import java.io.InputStream;

import ch.alpine.tensor.ext.Integers;

/** implementation is deliberately not thread-safe
 * 
 * capacity is chosen to be of the form 2^bits which allows to do fast
 * arithmetics */
public class ByteRingBuffer {
  /** @param bits for instance bits == 8 will give a buffer with 256 bytes capacity
   * @return instance of {@link ByteRingBuffer} with capacity 2^bits
   * @throws Exception if bits is greater than 24 */
  public static ByteRingBuffer withSizeInBitResolution(int bits) {
    Integers.requirePositive(bits);
    Integers.requireLessThan(bits, 25);
    return new ByteRingBuffer(bits);
  }

  // ---
  private final byte[] buffer;
  private final int mask;
  private int index_peek = 0;
  private int index_poke = 0;

  private ByteRingBuffer(int bits) {
    int size = 1 << bits;
    buffer = new byte[size];
    mask = size - 1; // for instance 0xFFFF
  }

  /** @return number bytes that can be read via {@link #peek(byte[], int)} */
  private int available() {
    return index_poke - index_peek & mask;
  }

  /** @param data buffer to copy bytes into
   * @param length number bytes to copy into given data array
   * @return whether data was filled with length number of bytes from buffer */
  public boolean peek(byte[] data, int length) {
    Integers.requireLessEquals(length, data.length); // check always
    if (length <= available()) {
      for (int index = 0; index < length; ++index)
        data[index] = buffer[index_peek + index & mask];
      return true;
    }
    return false;
  }

  /** Remarks:
   * does not throw an exception if the buffer "overrides" itself
   * input argument is integer that will be cast to byte
   * motivated by {@link InputStream#read()} design
   * 
   * @param value */
  public void append(int value) {
    buffer[index_poke & mask] = (byte) value;
    ++index_poke;
  }

  /** @param delta
   * @throws Exception */
  public void discard(int delta) {
    Integers.requireLessEquals(delta, available());
    index_peek += delta;
  }

  /** @return total number of bytes that can be written in sequence
   * until the buffer will start overwriting previous data */
  public int totalCapacity() {
    return buffer.length;
  }
}
