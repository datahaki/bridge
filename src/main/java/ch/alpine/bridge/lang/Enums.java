// code by jph, gjoel
package ch.alpine.bridge.lang;

import java.util.EnumSet;
import java.util.Set;

public enum Enums {
  ;
  public static <E extends Enum<E>> Set<E> setFromMask(Class<E> elementType, long mask) {
    Set<E> set = EnumSet.noneOf(elementType);
    for (E element : elementType.getEnumConstants()) {
      if ((mask & 1) == 1)
        set.add(element);
      mask >>= 1;
    }
    return set;
  }

  /** @param element
   * @return
   * @throws ArrayIndexOutOfBoundsException if element is already last entry of enum */
  @SuppressWarnings("unchecked")
  public static <E extends Enum<E>> E increment(E element) {
    Enum<?>[] enumConstants = element.getDeclaringClass().getEnumConstants();
    return (E) enumConstants[element.ordinal() + 1];
  }

  /** @param element
   * @return */
  @SuppressWarnings("unchecked")
  public static <E extends Enum<E>> E cycle(E element) {
    Enum<?>[] enumConstants = element.getDeclaringClass().getEnumConstants();
    return (E) enumConstants[Math.floorMod(element.ordinal() + 1, enumConstants.length)];
  }
}
