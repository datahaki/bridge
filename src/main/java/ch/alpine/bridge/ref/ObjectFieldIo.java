// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

/** allow all elements tracked by gui except for transient */
public abstract class ObjectFieldIo extends ObjectFieldBase {
  private static final Predicate<Field> IS_LEAF = VisibilityPredicate.of( //
      ObjectFieldAll.LEAF_DEMAND, //
      ObjectFieldAll.LEAF_REJECT //
          | Modifier.TRANSIENT);
  // ---
  private static final Predicate<Field> IS_NODE = VisibilityPredicate.of( //
      ObjectFieldAll.NODE_DEMAND, //
      ObjectFieldAll.NODE_REJECT //
          | Modifier.TRANSIENT);

  public ObjectFieldIo() {
    super(IS_LEAF, IS_NODE);
  }
}
