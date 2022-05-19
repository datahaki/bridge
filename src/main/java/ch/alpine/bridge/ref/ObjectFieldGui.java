// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

public abstract class ObjectFieldGui implements ObjectFieldVisitor {
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
  public final Type getType(Field field) {
    if (IS_LEAF.test(field))
      return Type.LEAF;
    if (IS_NODE.test(field))
      return Type.NODE;
    return Type.SKIP;
  }
}
