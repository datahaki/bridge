// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

/** in comparison to {@link ObjectFieldAll} this visitor skips transient
 * fields, which is intended for use with persistent storage */
public abstract class ObjectFieldIo implements ObjectFieldVisitor {
  private static final Predicate<Field> IS_LEAF = VisibilityPredicate.of( //
      ObjectFieldAll.LEAF_DEMAND, //
      ObjectFieldAll.LEAF_REJECT //
          | Modifier.TRANSIENT);
  // ---
  private static final Predicate<Field> IS_NODE = VisibilityPredicate.of( //
      ObjectFieldAll.NODE_DEMAND, //
      ObjectFieldAll.NODE_REJECT //
          | Modifier.TRANSIENT);

  @Override
  public final boolean isLeaf(Field field) {
    return IS_LEAF.test(field);
  }

  @Override
  public final boolean isNode(Field field) {
    return IS_NODE.test(field);
  }
}
