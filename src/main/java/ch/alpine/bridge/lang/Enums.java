// code by jph, gjoel
package ch.alpine.bridge.lang;

import java.util.EnumSet;
import java.util.Set;

public enum Enums {
  ;
  /** @param elementType
   * @param mask of up to 64 bits
   * @return set of elementTypes that contains enum of oridinal if mask & (1<<oridinal) != 0 */
  public static <T extends Enum<T>> Set<T> setFromMask(Class<T> elementType, long mask) {
    Set<T> set = EnumSet.noneOf(elementType);
    for (T element : elementType.getEnumConstants()) {
      if ((mask & 1) == 1)
        set.add(element);
      mask >>= 1;
    }
    return set;
  }

  /** @param element
   * @return
   * @throws ArrayIndexOutOfBoundsException if element is already last entry of enum */
  public static <T extends Enum<T>> T increment(T element) {
    T[] enumConstants = element.getDeclaringClass().getEnumConstants();
    return enumConstants[element.ordinal() + 1];
  }

  /** @param element
   * @return */
  public static <T extends Enum<T>> T cycle(T element) {
    T[] enumConstants = element.getDeclaringClass().getEnumConstants();
    return enumConstants[Math.floorMod(element.ordinal() + 1, enumConstants.length)];
  }
}
