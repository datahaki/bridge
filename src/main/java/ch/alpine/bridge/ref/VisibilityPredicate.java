// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public enum VisibilityPredicate {
  ;
  /** @param demand bit mask
   * @param reject bit mask disjoint with given demand bits
   * @return predicate that checks whether field has all demand bits
   * and none of the reject bits */
  public static Predicate<Field> of(int demand, int reject) {
    if ((demand & reject) == 0) {
      int mask = demand | reject;
      return field -> (field.getModifiers() & mask) == demand;
    }
    throw new IllegalArgumentException();
  }
}
