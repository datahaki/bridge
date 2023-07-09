// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JLabel;

import ch.alpine.bridge.ref.FieldPanel;
import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.FieldsEditorParam;
import ch.alpine.bridge.ref.ann.FieldFuse;
import ch.alpine.bridge.ref.ann.FieldLabels;

public class ToolbarFieldsEditor extends FieldsEditor {
  /** @param object
   * @param jComponent
   * @return fields editor to which callback functions may be attached via
   * {@link #addUniversalListener(Runnable)} */
  public static FieldsEditor addToComponent(Object object, JComponent jComponent) {
    return new ToolbarFieldsEditor(object, jComponent);
  }

  // ---
  private class Visitor extends ObjectFieldAll {
    private final JComponent jComponent;

    public Visitor(JComponent jComponent) {
      this.jComponent = jComponent;
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
      JComponent component = fieldPanel.getJComponent();
      if (field.getType().isEnum()) {
        component.setToolTipText(text);
      } else //
      if (!isBoolean) {
        JLabel jLabel = FieldsEditorParam.GLOBAL.createLabel(text + " ");
        jLabel.setToolTipText(FieldToolTip.of(field));
        jComponent.add(jLabel);
      }
      jComponent.add(component);
      // some look and feels introduce a vertical line | as separator...
      // jToolBar.addSeparator();
      jComponent.add(new JLabel("\u2000"));
    }
  }

  private ToolbarFieldsEditor(Object object, JComponent jToolBar) {
    ObjectFields.of(object, new Visitor(jToolBar));
  }
}
