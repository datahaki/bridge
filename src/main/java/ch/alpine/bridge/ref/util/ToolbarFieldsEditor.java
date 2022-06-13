// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.Font;
import java.lang.reflect.Field;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import ch.alpine.bridge.ref.FieldPanel;
import ch.alpine.bridge.ref.FieldToolTip;
import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ObjectFieldGui;
import ch.alpine.bridge.ref.ObjectFields;
import ch.alpine.bridge.ref.ann.FieldFuse;
import ch.alpine.bridge.ref.ann.FieldLabels;

// TODO BRIDGE field fuse -> button text as variable name!
public class ToolbarFieldsEditor extends FieldsEditor {
  /** @param object
   * @param jToolBar
   * @return fields editor to which callback functions may be attached via
   * {@link #addUniversalListener(Runnable)} */
  public static FieldsEditor add(Object object, JToolBar jToolBar) {
    return new ToolbarFieldsEditor(object, jToolBar);
  }

  private class Visitor extends ObjectFieldGui {
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
      FieldPanel fieldPanel = register(isBoolean && !isFuse //
          ? new TogglePanel(fieldWrap, text, (Boolean) value)
          : fieldWrap.createFieldPanel(object, value), fieldWrap, object);
      JComponent jComponent = layout(field, fieldPanel.getJComponent());
      if (field.getType().isEnum()) {
        jComponent.setToolTipText(text);
      } else //
        if (!isBoolean) {
          JLabel jLabel = new JLabel(text + " ");
          Font font = jLabel.getFont();
          final int _style = font.getStyle();
          int style = _style;
          if (_style == Font.BOLD) // for default look and feel
            style = Font.PLAIN;
          if (_style == Font.PLAIN)
            style = Font.ITALIC;
          jLabel.setFont(font.deriveFont(style));
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
