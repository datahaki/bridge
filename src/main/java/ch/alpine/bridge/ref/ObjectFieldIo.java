// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

/** disallow transient */
public abstract class ObjectFieldIo implements ObjectFieldVisitor {
  private static final int LEAF_FILTER = ObjectFieldGui.LEAF_FILTER;
  private static final int LEAF_TESTED = ObjectFieldGui.LEAF_TESTED | Modifier.TRANSIENT;
  private static final Predicate<Field> IS_LEAF = VisibilityPredicate.field( //
      LEAF_FILTER, //
      LEAF_TESTED);
  // ---
  private static final int NODE_FILTER = ObjectFieldGui.NODE_FILTER;
  private static final int NODE_TESTED = ObjectFieldGui.NODE_TESTED | Modifier.TRANSIENT;
  private static final Predicate<Field> IS_NODE = VisibilityPredicate.field( //
      NODE_FILTER, //
      NODE_TESTED);

  @Override
  public final Type getType(Field field) {
    Class<?> class_field = field.getType();
    if (FieldWraps.INSTANCE.elemental(class_field)) {
      if (IS_LEAF.test(field))
        return Type.LEAF;
    } else
      if (IS_NODE.test(field))
        return Type.NODE;
    return Type.SKIP;
  }
}
