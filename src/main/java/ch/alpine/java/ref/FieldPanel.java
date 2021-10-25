// code by jph, gjoel
package ch.alpine.java.ref;

import java.awt.Font;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JComponent;

import ch.alpine.java.ref.gui.FieldsEditor;

public abstract class FieldPanel {
  public static final Font FONT = new Font(Font.DIALOG_INPUT, Font.PLAIN, 18);
  // ---
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

  /** Remark: the function should not be invoked by the application layer
   * 
   * @return component to be shown in the {@link FieldsEditor} */
  public abstract JComponent getJComponent();

  /** The function does not invoke {@link #notifyListeners(String)}
   * 
   * Remark: the function should not be invoked by the application layer
   * 
   * @param value is non-null */
  public abstract void updateJComponent(Object value);
}
