// code by jph
package ch.ethz.idsc.tensor.ref.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JComponent;

import ch.ethz.idsc.tensor.ref.FieldType;

public abstract class FieldPanel {
  static final Color FAIL = new Color(255, 192, 192);
  static final Font FONT = new Font(Font.DIALOG_INPUT, Font.PLAIN, 18);
  // ---
  private final List<Consumer<String>> list = new LinkedList<>();
  private final FieldType fieldType;

  public FieldPanel(FieldType fieldType) {
    this.fieldType = fieldType;
  }

  public final void addListener(Consumer<String> consumer) {
    list.add(consumer);
  }

  public final void notifyListeners(String text) {
    list.forEach(consumer -> consumer.accept(text));
  }

  public abstract JComponent getJComponent();

  public final FieldType getFieldType() {
    return fieldType;
  }
}
