// code by jph
package ch.alpine.java.ref.gui;

import java.lang.reflect.Field;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JToolBar;

import ch.alpine.java.ref.BooleanFieldWrap;
import ch.alpine.java.ref.FieldPanel;
import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.ObjectFieldVisitor;
import ch.alpine.java.ref.ObjectFields;
import ch.alpine.java.ref.ann.FieldFuse;
import ch.alpine.java.ref.ann.FieldLabels;

public class ToolbarFieldsEditor extends FieldsEditor {
  /** @param object
   * @param jToolBar */
  public static ToolbarFieldsEditor add(Object object, JToolBar jToolBar) {
    return new ToolbarFieldsEditor(object, jToolBar);
  }

  private class Visitor implements ObjectFieldVisitor {
    private final JToolBar jToolBar;

    public Visitor(JToolBar jToolBar) {
      this.jToolBar = jToolBar;
    }

    @Override // from ObjectFieldVisitor
    public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
      Field field = fieldWrap.getField();
      String text = FieldLabels.of(key, field, null);
      boolean isBoolean = fieldWrap instanceof BooleanFieldWrap;
      boolean isFuse = Objects.nonNull(field.getAnnotation(FieldFuse.class));
      FieldPanel fieldPanel = isBoolean && !isFuse //
          ? new TogglePanel(fieldWrap, text, (Boolean) value)
          : fieldWrap.createFieldPanel(value);
      list.add(fieldPanel);
      fieldPanel.addListener(string -> fieldWrap.setIfValid(object, string));
      if (!isBoolean) {
        JLabel jLabel = new JLabel(text + " ");
        jLabel.setToolTipText(FieldToolTip.of(field));
        jToolBar.add(jLabel);
      }
      jToolBar.add(StaticHelper.layout(field, fieldPanel.getJComponent()));
      jToolBar.addSeparator();
    }
  }

  private ToolbarFieldsEditor(Object object, JToolBar jToolBar) {
    ObjectFields.of(object, new Visitor(jToolBar));
  }
}
