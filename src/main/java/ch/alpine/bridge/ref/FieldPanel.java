// code by jph, gjoel
package ch.alpine.bridge.ref;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JComponent;

import ch.alpine.bridge.ref.util.FieldsEditor;

/** base class for all gui elements managed by {@link FieldsEditor} */
public abstract class FieldPanel {
  private final List<Consumer<String>> list = new LinkedList<>();
  private final FieldWrap fieldWrap;

  /** @param fieldWrap non-null */
  protected FieldPanel(FieldWrap fieldWrap) {
    this.fieldWrap = Objects.requireNonNull(fieldWrap);
  }

  /** @return information about the field this gui element wraps around */
  public final FieldWrap fieldWrap() {
    return fieldWrap;
  }

  /** @param consumer will be provided with the string expression of the value
   * edited in the gui */
  public final void addListener(Consumer<String> consumer) {
    list.add(consumer);
  }

  /** function invoked by the gui element that the value was edited in the gui
   * 
   * @param text string expression of value after edit */
  protected final void notifyListeners(String text) {
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
