// code by jph
package ch.alpine.java.ref.gui;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import ch.alpine.java.ref.FieldPanel;
import ch.alpine.java.ref.FieldWrap;

public abstract class FieldsEditor {
  private final List<FieldPanel> list = new LinkedList<>();

  /** @param fieldPanel
   * @param fieldWrap
   * @param object
   * @return given fieldPanel */
  protected FieldPanel register(FieldPanel fieldPanel, FieldWrap fieldWrap, Object object) {
    fieldPanel.addListener(string -> fieldWrap.setIfValid(object, string));
    list.add(fieldPanel);
    return fieldPanel;
  }

  /** @return field panel for each field in the object that appears in the editor */
  public final List<FieldPanel> list() {
    return Collections.unmodifiableList(list);
  }

  /** @param runnable that will be run if any value in editor was subject to change */
  public final void addUniversalListener(Runnable runnable) {
    Consumer<String> consumer = string -> runnable.run();
    list.forEach(fieldPanel -> fieldPanel.addListener(consumer));
  }
}
