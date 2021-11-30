// code by jph, gjoel
package ch.alpine.java.ref.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ch.alpine.java.ref.FieldPanel;
import ch.alpine.java.ref.FieldWrap;

// TODO move fieldsEditor and implementations to ref.util package
public abstract class FieldsEditor {
  /** the association of FieldPanel and Object is required.
   * In a field editor instance, the object as value in the map is not necessary unique. */
  private static class Entry {
    private final FieldPanel fieldPanel;
    private final Object object;

    public Entry(FieldPanel fieldPanel, Object object) {
      this.fieldPanel = fieldPanel;
      this.object = object;
    }

    public FieldPanel getFieldPanel() {
      return fieldPanel;
    }

    public void updateJComponent() {
      Object value = null;
      try {
        value = fieldPanel.fieldWrap().getField().get(object);
      } catch (IllegalAccessException illegalAccessException) {
        illegalAccessException.printStackTrace();
      }
      if (Objects.nonNull(value))
        // any exception caused by implementation based on presented value is deliberately not caught
        fieldPanel.updateJComponent(value);
    }
  }

  private final List<Entry> list = new LinkedList<>();

  /** @param fieldPanel
   * @param fieldWrap
   * @param object
   * @return given fieldPanel */
  protected final FieldPanel register(FieldPanel fieldPanel, FieldWrap fieldWrap, Object object) {
    list.add(new Entry(fieldPanel, object));
    fieldPanel.addListener(string -> fieldWrap.setIfValid(object, string));
    return fieldPanel;
  }

  /** @return field panel for each field in the object that appears in the editor */
  public final List<FieldPanel> list() {
    return list.stream().map(Entry::getFieldPanel).collect(Collectors.toList());
  }

  /** @param runnable that will be run if any value in editor was subject to change */
  public final void addUniversalListener(Runnable runnable) {
    Consumer<String> consumer = string -> runnable.run();
    list.forEach(entry -> entry.getFieldPanel().addListener(consumer));
  }

  /** in case the object field values have been modified outside the gui, invoking
   * {@link #updateJComponents()} causes the gui components to update their
   * appearance based on the new value. */
  public final void updateJComponents() {
    list.forEach(Entry::updateJComponent);
  }
}
