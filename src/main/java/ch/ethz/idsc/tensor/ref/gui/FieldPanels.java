// code by jph
package ch.ethz.idsc.tensor.ref.gui;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import ch.ethz.idsc.tensor.ref.FieldType;
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
  private final Map<Field, FieldPanel> map = new LinkedHashMap<>();

  private FieldPanels(Object object) {
    ObjectProperties objectProperties = ObjectProperties.wrap(object);
    Map<Field, FieldType> fieldMap = objectProperties.fields();
    for (Entry<Field, FieldType> entry : fieldMap.entrySet()) {
      Field field = entry.getKey();
      FieldType fieldType = entry.getValue();
      try {
        Object value = field.get(object); // check for failure, value only at begin!
        FieldPanel fieldPanel = factor(field, fieldType, value);
        fieldPanel.addListener(string -> objectProperties.setIfValid(field, fieldType, string));
        map.put(field, fieldPanel);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }

  public Map<Field, FieldPanel> map() {
    return Collections.unmodifiableMap(map);
  }

  public void addUniversalListener(Consumer<String> consumer) {
    map.values().stream().forEach(fieldPanel -> fieldPanel.addListener(consumer));
  }

  private static FieldPanel factor(Field field, FieldType fieldType, Object value) {
    switch (fieldType) {
    case STRING:
    case TENSOR:
    case SCALAR:
      return new StringPanel(field, fieldType, value);
    case BOOLEAN:
      return new BooleanPanel((Boolean) value);
    case ENUM:
      return new EnumPanel(field.getType().getEnumConstants(), value);
    case FILE:
      return new FilePanel(field, fieldType, (File) value);
    case COLOR:
      return new ColorPanel(field, fieldType, value);
    }
    throw new RuntimeException();
  }
}
