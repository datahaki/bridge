// code by jph
package ch.alpine.java.ref.gui;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import ch.alpine.java.ref.FieldPanel;

public abstract class FieldsEditor {
  protected final List<FieldPanel> list = new LinkedList<>();

  /** @return field panel for each field in the object that appears in the editor */
  public final List<FieldPanel> list() {
    return Collections.unmodifiableList(list);
  }

  /** @param runnable that will be run if any value in editor was subject to change */
  public void addUniversalListener(Runnable runnable) {
    Consumer<String> consumer = string -> runnable.run();
    list.forEach(fieldPanel -> fieldPanel.addListener(consumer));
  }
}
