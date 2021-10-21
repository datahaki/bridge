// code by jph
package ch.alpine.java.ref.gui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import ch.alpine.java.ref.FieldPanel;
import ch.alpine.java.ref.FieldWrap;

public abstract class FieldsEditor {
  // private final List<FieldPanel> list = new LinkedList<>();
  private final Map<FieldPanel, Object> map = new LinkedHashMap<>();
  private final BiConsumer<FieldPanel, Object> sync = new BiConsumer<>() {
    @Override
    public void accept(FieldPanel fieldPanel, Object object) {
      try {
        Object value = fieldPanel.fieldWrap().getField().get(object);
        fieldPanel.update(value);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  };

  /** @param fieldPanel
   * @param fieldWrap
   * @param object
   * @return given fieldPanel */
  protected FieldPanel register(FieldPanel fieldPanel, FieldWrap fieldWrap, Object object) {
    fieldPanel.addListener(string -> fieldWrap.setIfValid(object, string));
    // list.add(fieldPanel);
    map.put(fieldPanel, object);
    return fieldPanel;
  }

  /** @return field panel for each field in the object that appears in the editor */
  public final List<FieldPanel> list() {
    // return Collections.unmodifiableList(list);
    return List.copyOf(map.keySet());
  }

  /** @param runnable that will be run if any value in editor was subject to change */
  public final void addUniversalListener(Runnable runnable) {
    Consumer<String> consumer = string -> runnable.run();
    // list.forEach(fieldPanel -> fieldPanel.addListener(consumer));
    map.keySet().forEach(fieldPanel -> fieldPanel.addListener(consumer));
  }

  public final void sync() {
    map.forEach(sync);
  }
}
