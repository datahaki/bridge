// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import ch.alpine.bridge.ref.FieldPanel;
import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.FieldsEditorParam;
import ch.alpine.bridge.ref.ann.FieldFuse;
import ch.alpine.bridge.ref.ann.FieldLabels;

public class ToolbarFieldsEditor extends FieldsEditor {
  /** @param object
   * @param jToolBar
   * @return fields editor to which callback functions may be attached via
   * {@link #addUniversalListener(Runnable)} */
  public static FieldsEditor add(Object object, JToolBar jToolBar) {
    return new ToolbarFieldsEditor(object, jToolBar);
  }

  private class Visitor extends ObjectFieldAll {
    private final JToolBar jToolBar;

    public Visitor(JToolBar jToolBar) {
      this.jToolBar = jToolBar;
    }

    @Override // from ObjectFieldVisitor
    public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
      Field field = fieldWrap.getField();
      String text = FieldLabels.of(key, field, null);
      boolean isBoolean = field.getType().equals(Boolean.class);
      boolean isFuse = Objects.nonNull(field.getAnnotation(FieldFuse.class));
      FieldPanel fieldPanel = isBoolean && !isFuse //
          ? new TogglePanel(fieldWrap, text, (Boolean) value)
          : fieldWrap.createFieldPanel(object, value);
      register(fieldPanel, object);
      JComponent jComponent = fieldPanel.getJComponent();
      if (field.getType().isEnum()) {
        jComponent.setToolTipText(text);
      } else //
      if (!isBoolean) {
        JLabel jLabel = FieldsEditorParam.GLOBAL.createLabel(text + " ");
        jLabel.setToolTipText(FieldToolTip.of(field));
        jToolBar.add(jLabel);
      }
      jToolBar.add(jComponent);
      // some look and feels introduce a vertical line | as separator...
      // jToolBar.addSeparator();
      jToolBar.add(new JLabel("\u2000"));
    }
  }

  private ToolbarFieldsEditor(Object object, JToolBar jToolBar) {
    ObjectFields.of(object, new Visitor(jToolBar));
  }
}
