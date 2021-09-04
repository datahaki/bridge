// code by jph
package ch.alpine.java.ref.gui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.ObjectProperties;

/** FieldPanels performs introspection of an instance of a public class
 * with public, non-final fields. Depending on the field types a gui element
 * will be created through which the user can modify the value of the
 * field of the given instance.
 * 
 * see FieldWraps */
public class FieldPanels {
  /** @param object
   * @return */
  public static FieldPanels of(Object object) {
    return new FieldPanels(object);
  }

  /***************************************************/
  // TODO technically objectProperties should not be needed!
  private final ObjectProperties objectProperties;
  private final List<FieldPanel> list = new ArrayList<>();

  private FieldPanels(Object object) {
    objectProperties = ObjectProperties.wrap(object);
    for (FieldWrap fieldWrap : objectProperties.list()) {
      Field field = fieldWrap.getField();
      try {
        // fail fast
        FieldPanel fieldPanel = fieldWrap.createFieldPanel(field.get(object));
        fieldPanel.addListener(string -> objectProperties.setIfValid(fieldWrap, string));
        list.add(fieldPanel);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }

  public ObjectProperties objectProperties() {
    return objectProperties;
  }

  public List<FieldPanel> list() {
    return Collections.unmodifiableList(list);
  }

  public void addUniversalListener(Consumer<String> consumer) {
    list.stream().forEach(fieldPanel -> fieldPanel.addListener(consumer));
  }
}
