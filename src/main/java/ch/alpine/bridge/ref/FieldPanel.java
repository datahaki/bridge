// code by jph, gjoel
package ch.alpine.bridge.ref;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JComponent;

import ch.alpine.bridge.ref.util.FieldsEditor;

public abstract class FieldPanel {
  private final List<Consumer<String>> list = new LinkedList<>();
  private final FieldWrap fieldWrap;

  public FieldPanel(FieldWrap fieldWrap) {
    this.fieldWrap = fieldWrap;
  }

  public final FieldWrap fieldWrap() {
    return fieldWrap;
  }

  public final void addListener(Consumer<String> consumer) {
    list.add(consumer);
  }

  public final void notifyListeners(String text) {
    list.forEach(consumer -> consumer.accept(text));
  }

  /** Remark: The function should not be invoked by the application layer.
   * Repeated calls to this function return the same instance.
   * 
   * @return component to be shown in the {@link FieldsEditor} */
  public abstract JComponent getJComponent();

  /** Remark: The function should not be invoked by the application layer.
   * The function does not invoke {@link #notifyListeners(String)}
   * 
   * @param value is non-null */
  public abstract void updateJComponent(Object value);
}
