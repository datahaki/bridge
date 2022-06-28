// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;
import java.util.function.Predicate;

import ch.alpine.bridge.ref.FieldWraps;

public abstract class ObjectFieldBase implements ObjectFieldVisitor {
  private final Predicate<Field> isLeaf;
  private final Predicate<Field> isNode;

  public ObjectFieldBase(Predicate<Field> isLeaf, Predicate<Field> isNode) {
    this.isLeaf = isLeaf;
    this.isNode = isNode;
  }

  @Override // from ObjectFieldVisitor
  public final Type classify(Field field) {
    Class<?> class_field = field.getType();
    if (FieldWraps.INSTANCE.elemental(class_field)) {
      if (isLeaf.test(field))
        return Type.LEAF;
    } else
      if (isNode.test(field))
        return Type.NODE;
    return Type.SKIP;
  }
}
