// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

/** visits all fields that are covered by reflection */
public abstract class ObjectFieldAll implements ObjectFieldVisitor {
  static final int LEAF_DEMAND = 0 //
      | Modifier.PUBLIC;
  static final int LEAF_REJECT = 0 //
      | Modifier.FINAL //
      | Modifier.STATIC;
  private static final Predicate<Field> IS_LEAF = VisibilityPredicate.of( //
      LEAF_DEMAND, //
      LEAF_REJECT);
  // ---
  static final int NODE_DEMAND = 0 //
      | Modifier.PUBLIC //
      | Modifier.FINAL;
  static final int NODE_REJECT = 0 //
      | Modifier.STATIC;
  private static final Predicate<Field> IS_NODE = VisibilityPredicate.of( //
      NODE_DEMAND, //
      NODE_REJECT);

  @Override
  public final boolean isLeaf(Field field) {
    return IS_LEAF.test(field);
  }

  @Override
  public final boolean isNode(Field field) {
    return IS_NODE.test(field);
  }
}
