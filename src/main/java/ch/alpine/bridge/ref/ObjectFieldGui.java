// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

public abstract class ObjectFieldGui implements ObjectFieldVisitor {
  static final int LEAF_FILTER = Modifier.PUBLIC;
  static final int LEAF_TESTED = LEAF_FILTER //
      | Modifier.PRIVATE | Modifier.PROTECTED //
      | Modifier.FINAL | Modifier.STATIC;
  private static final Predicate<Field> IS_LEAF = VisibilityPredicate.field( //
      LEAF_FILTER, //
      LEAF_TESTED);
  // ---
  static final int NODE_FILTER = Modifier.PUBLIC | Modifier.FINAL;
  static final int NODE_TESTED = NODE_FILTER //
      | Modifier.PRIVATE | Modifier.PROTECTED //
      | Modifier.FINAL | Modifier.STATIC;
  private static final Predicate<Field> IS_NODE = VisibilityPredicate.field( //
      NODE_FILTER, //
      NODE_TESTED);

  @Override
  public final Type getType(Field field) {
    if (IS_LEAF.test(field))
      return Type.LEAF;
    if (IS_NODE.test(field))
      return Type.NODE;
    return Type.SKIP;
  }
}
