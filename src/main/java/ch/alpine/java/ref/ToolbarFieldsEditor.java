// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolBar;

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
      FieldPanel fieldPanel = register(isBoolean && !isFuse //
          ? new TogglePanel(fieldWrap, text, (Boolean) value)
          : fieldWrap.createFieldPanel(value), fieldWrap, object);
      JComponent jComponent = StaticHelper.layout(field, fieldPanel.getJComponent());
      if (fieldWrap instanceof EnumFieldWrap) {
        jComponent.setToolTipText(text);
      } else //
      if (!isBoolean) {
        JLabel jLabel = new JLabel(text + " ");
        jLabel.setToolTipText(FieldToolTip.of(field));
        jToolBar.add(jLabel);
      }
      jToolBar.add(jComponent);
      jToolBar.addSeparator();
    }
  }

  private ToolbarFieldsEditor(Object object, JToolBar jToolBar) {
    ObjectFields.of(object, new Visitor(jToolBar));
  }
}
