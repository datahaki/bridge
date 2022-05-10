// code by jph
package ch.alpine.bridge.lang;

import java.util.EnumSet;
import java.util.Set;

public enum Enums {
  ;
  /** @param elementType
   * @param mask
   * @return */
  public static <E extends Enum<E>> Set<E> setFromMask(Class<E> elementType, long mask) {
    Set<E> set = EnumSet.noneOf(elementType);
    for (E element : elementType.getEnumConstants()) {
      if ((mask & 1) == 1)
        set.add(element);
      mask >>= 1;
    }
    return set;
  }

  /** Hint: the enum class <b>cannot</b> always be determined via element.getClass.
   * One could determine the enum class from given element via enclosing class etc.
   * The API was chosen for simplicity.
   * 
   * @param elementType
   * @param element
   * @return
   * @throws Exception if element is already last entry of enum */
  @SuppressWarnings("unchecked")
  public static <E extends Enum<E>> E increment(Class<E> elementType, E element) {
    Enum<?>[] enumConstants = elementType.getEnumConstants();
    return (E) enumConstants[element.ordinal() + 1];
  }
}
