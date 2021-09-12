// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public enum VisibilityPredicate {
  ;
  /** @param subset
   * @param set
   * @return whether field is public, non final, non static, non transient */
  public static Predicate<Field> field(int subset, int set) {
    if ((set & subset) == subset)
      return field -> (field.getModifiers() & set) == subset;
    throw new IllegalArgumentException();
  }
}
