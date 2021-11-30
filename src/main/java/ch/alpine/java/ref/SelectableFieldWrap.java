// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import ch.alpine.java.ref.ann.FieldSelection;

/* package */ abstract class SelectableFieldWrap extends BaseFieldWrap {
  private final FieldSelection fieldSelection;

  public SelectableFieldWrap(Field field) {
    super(field);
    fieldSelection = field.getAnnotation(FieldSelection.class);
    // Class<?> declaringClass = field.getDeclaringClass();
    // System.out.println(declaringClass+" "+field.getName());
    // try {
    // Method method = declaringClass.getMethod(field.getName()+"Values");
    // method.invoke(method, null);
    // } catch (Exception exception) {
    // // ---
    // }
    // new FieldSelection() {
    // @Override
    // public Class<? extends Annotation> annotationType() {
    // // TODO Auto-generated method stub
    // return null;
    // }
    //
    // @Override
    // public String[] array() {
    // // TODO Auto-generated method stub
    // return null;
    // }
    // };
  }

  @Override // from FieldWrap
  public String toString(Object object) {
    return object.toString();
  }

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object value) {
    if (Objects.nonNull(fieldSelection))
      try {
        return new MenuPanel(this, value, () -> Arrays.asList(fieldSelection.array()));
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    return new StringPanel(this, value);
  }
}
