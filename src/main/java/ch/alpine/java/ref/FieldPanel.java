// code by jph
package ch.alpine.java.ref;

import java.awt.Font;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JComponent;

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

  /** @param consumer */
  public final void addListener(Consumer<String> consumer) {
    list.add(consumer);
  }

  /** @param text */
  public final void notifyListeners(String text) {
    list.forEach(consumer -> consumer.accept(text));
  }

  /** @return */
  public abstract JComponent getJComponent();

  public void update(Object value) {
    // TODO make abstract
  }
}
