// code by jph
package ch.ethz.idsc.tensor.ref.gui;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;

import ch.ethz.idsc.tensor.ref.FieldColor;
import ch.ethz.idsc.tensor.ref.FieldType;
import ch.ethz.idsc.tensor.ref.ObjectProperties;

public class FieldPanels {
  public static FieldPanels of(Object object, Object reference) {
    // TODO assert that object and reference are of the same type!
    return new FieldPanels(object, reference);
  }

  /***************************************************/
  private final Map<Field, FieldPanel> map = new LinkedHashMap<>();
  // private final List<Consumer<Field>> list = new LinkedList<>();

  private FieldPanels(Object object, Object reference) {
    ObjectProperties objectProperties = ObjectProperties.wrap(object);
    Map<Field, FieldType> fieldMap = objectProperties.fields();
    for (Entry<Field, FieldType> entry : fieldMap.entrySet()) {
      Field field = entry.getKey();
      FieldType type = entry.getValue();
      try {
        Object value = field.get(object); // check for failure, value only at begin!
        FieldPanel fieldPanel = factor(field, type, value);
        fieldPanel.addListener(string -> {
          // boolean valid =
          objectProperties.setIfValid(field, type, string);
          // if (valid)
          // list.forEach(consumer -> consumer.accept(field));
        });
        map.put(field, fieldPanel);
      } catch (Exception exception) {
        // ---
      }
    }
  }

  public Map<Field, FieldPanel> map() {
    return Collections.unmodifiableMap(map);
  }
  // public void addChangeConsumer(Consumer<Field> consumer) {
  // list.add(consumer);
  // }

  // TODO 20201126 JAN this listener is also notified about invalid values -> not good
  public void addUniversalListener(Consumer<String> consumer) {
    map.values().stream().forEach(fieldPanel -> fieldPanel.addListener(consumer));
  }

  private static FieldPanel factor(Field field, FieldType fieldType, Object value) {
    switch (fieldType) {
    case TENSOR: {
      FieldColor fieldColor = field.getAnnotation(FieldColor.class);
      if (Objects.nonNull(fieldColor))
        return new ColorPanel(field, fieldType, value);
    }
    case STRING:
    case SCALAR:
      return new StringPanel(field, fieldType, value);
    case BOOLEAN:
      return new BooleanPanel((Boolean) value);
    case ENUM:
      return new EnumPanel(field.getType().getEnumConstants(), value);
    case FILE:
      return new FilePanel(field, fieldType, (File) value);
    }
    throw new RuntimeException();
  }
}
