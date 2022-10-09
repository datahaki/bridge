// code by jph
package ch.alpine.bridge.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.function.Predicate;

/** one application is to manage the registers for modbus communication
 * and ensure that write operations satisfy certain requirements
 * 
 * @implSpec
 * implementation is not thread safe */
public class VerifiedArray {
  /** @param length non-negative
   * @return */
  public static VerifiedArray of(int length) {
    return new VerifiedArray(length);
  }

  // ---
  private final CopyOnWriteLinkedSet<Predicate<ByteBuffer>> predicates = new CopyOnWriteLinkedSet<>();
  private final byte[] data;

  /** @param length non-negative */
  private VerifiedArray(int length) {
    data = new byte[length];
  }

  public int length() {
    return data.length;
  }

  /** the predicate is provided a byte buffer in BIG_ENDIAN encoding
   * with access to the full data starting at position == 0.
   * 
   * @param predicate non-null
   * @throws Exception if predicate was already added */
  public void addPredicate(Predicate<ByteBuffer> predicate) {
    predicates.add(predicate);
  }

  public void removePredicate(Predicate<ByteBuffer> predicate) {
    predicates.remove(predicate);
  }

  /** @param offset non-negative
   * @param peek
   * @return whether peek was filled with data from the buffer */
  public boolean peek(int offset, byte[] peek) {
    if (0 <= offset && //
        peek.length <= data.length && //
        peek.length <= data.length - offset) { // do not simplify conditions!
      ByteBuffer.wrap(data, offset, peek.length).get(peek);
      return true;
    }
    return false;
  }

  /** @param offset non-negative
   * @param poke
   * @return whether content was inserted into array at offset
   * @throws Exception if any predicate throws an exception in which case
   * the content of the data is not altered */
  public boolean poke(int offset, byte[] poke) {
    if (0 <= offset && //
        poke.length <= data.length && //
        poke.length <= data.length - offset) { // do not simplify conditions!
      byte[] copy = data.clone();
      ByteBuffer.wrap(copy, offset, poke.length).put(poke);
      boolean allMatch = predicates.stream().allMatch(predicate -> predicate.test(ByteBuffer.wrap(copy).order(ByteOrder.BIG_ENDIAN)));
      if (allMatch)
        ByteBuffer.wrap(data, offset, poke.length).put(poke);
      return allMatch;
    }
    return false;
  }
}
