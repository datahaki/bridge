// code by jph
package ch.ethz.idsc.tensor.ref.gui;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import ch.ethz.idsc.tensor.ref.FieldWrap;
import ch.ethz.idsc.tensor.ref.ObjectProperties;

/** FieldPanels performs introspection of an instance of a public class
 * with public, non-final fields. Depending on the field types a gui element
 * will be created through which the user can modify the value of the
 * field of the given instance.
 * 
 * Not all fields are possible to edits. The list of fields that produce
 * a gui element are:
 * Boolean (with B caps, and not boolean!)
 * String
 * Scalar
 * Tensor
 * Enum
 * File */
public class FieldPanels {
  /** @param object
   * @return */
  public static FieldPanels of(Object object) {
    return new FieldPanels(object);
  }

  /***************************************************/
  private final ObjectProperties objectProperties;
  private final List<FieldPanel> map = new LinkedList<>();

  private FieldPanels(Object object) {
    objectProperties = ObjectProperties.wrap(object);
    List<FieldWrap> fieldMap = objectProperties.fields();
    for (FieldWrap fieldType : fieldMap) {
      Field field = fieldType.getField();
      try {
        Object value = field.get(object); // check for failure, value only at begin!
        FieldPanel fieldPanel = fieldType.createFieldPanel(value);
        fieldPanel.addListener(string -> objectProperties.setIfValid(fieldType, string));
        map.add(fieldPanel);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }

  public ObjectProperties objectProperties() {
    return objectProperties;
  }

  public List<FieldPanel> map() {
    return Collections.unmodifiableList(map);
  }

  public void addUniversalListener(Consumer<String> consumer) {
    map.stream().forEach(fieldPanel -> fieldPanel.addListener(consumer));
  }
}
