// code by jph
// http://stackoverflow.com/questions/3305059/how-do-you-calculate-log-base-2-in-java-for-integers
package sys.mat;

import java.util.Collection;

import ch.alpine.tensor.ext.Integers;

public enum IntegerMath {
  ;
  /** Euclid's algorithm
   * 
   * @param a
   * @param b
   * @return greatest common divider of a and b. */
  public static int gcd(int a, int b) {
    Integers.requirePositiveOrZero(a);
    Integers.requirePositiveOrZero(b);
    return b == 0 //
        ? a
        : gcd(b, a % b);
  }

  public static int lcm(int a, int b) {
    return Math.multiplyExact(a, divideExact(b, gcd(a, b)));
  }

  /** @param collection non-empty
   * @return greatest common divider of all integers in collection */
  public static int gcd(Collection<Integer> collection) {
    return collection.stream().reduce(IntegerMath::gcd).orElseThrow();
  }

  /** @param collection non-empty
   * @return least common multiple of all integers in collection */
  public static int lcm(Collection<Integer> collection) {
    return collection.stream().reduce(IntegerMath::lcm).orElseThrow();
  }

  /** @param ofs
   * @param mod
   * @param min
   * @return minimal s with s ==_mod ofs && min <= s */
  public static int findMin(int ofs, int mod, int min) {
    int value = ofs - Math.floorDiv(ofs - min, mod) * mod;
    if (value < min || min + mod <= value)
      throw new RuntimeException();
    return value;
  }

  // TODO MIDI JAVA VERSION
  public static int ceilDiv(int x, int y) {
    final int q = x / y;
    // if the signs are the same and modulo not zero, round up
    if ((x ^ y) >= 0 && (q * y != x)) {
      return q + 1;
    }
    return q;
  }

  // TODO MIDI JAVA VERSION
  public static int divideExact(int x, int y) {
    int q = x / y;
    if ((x & y & q) >= 0) {
      return q;
    }
    throw new ArithmeticException("integer overflow");
  }
}
