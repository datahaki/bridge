// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

/** allow all elements tracked by gui except for transient */
public abstract class ObjectFieldIo implements ObjectFieldVisitor {
  private static final Predicate<Field> IS_LEAF = VisibilityPredicate.of( //
      ObjectFieldGui.LEAF_DEMAND, //
      ObjectFieldGui.LEAF_REJECT //
          | Modifier.TRANSIENT);
  // ---
  private static final Predicate<Field> IS_NODE = VisibilityPredicate.of( //
      ObjectFieldGui.NODE_DEMAND, //
      ObjectFieldGui.NODE_REJECT //
          | Modifier.TRANSIENT);

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
