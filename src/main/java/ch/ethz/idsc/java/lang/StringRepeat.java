// code by jph
package ch.ethz.idsc.java.lang;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/StringRepeat.html">StringRepeat</a> */
public enum StringRepeat {
  ;
  /** @param string
   * @param n
   * @return */
  public static String of(String string, int n) {
    return IntStream.range(0, n).mapToObj(i -> string).collect(Collectors.joining());
  }
}
