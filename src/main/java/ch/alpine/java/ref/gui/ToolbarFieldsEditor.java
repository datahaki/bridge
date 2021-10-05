// code by jph
package ch.alpine.java.ref.gui;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import ch.alpine.java.ref.FieldPanel;
import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.ObjectFieldVisitor;
import ch.alpine.java.ref.ObjectFields;
import ch.alpine.java.ref.ann.FieldLabels;
import ch.alpine.java.ref.ann.FieldPreferredWidth;

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
      FieldPanel fieldPanel = fieldWrap.createFieldPanel(value);
      list.add(fieldPanel);
      fieldPanel.addListener(string -> fieldWrap.setIfValid(object, string));
      Field field = fieldWrap.getField();
      {
        JLabel jLabel = createJLabel(FieldLabels.of(key, field, null));
        jLabel.setToolTipText(FieldToolTip.of(field));
        jToolBar.add(jLabel);
      }
      {
        JComponent jComponent = fieldPanel.getJComponent();
        {
          FieldPreferredWidth fieldPreferredWidth = field.getAnnotation(FieldPreferredWidth.class);
          if (Objects.nonNull(fieldPreferredWidth)) {
            Dimension dimension = jComponent.getPreferredSize();
            dimension.width = Math.max(dimension.width, fieldPreferredWidth.width());
            jComponent.setPreferredSize(dimension);
          }
        }
        jToolBar.add(jComponent);
      }
      jToolBar.addSeparator();
    }
  }

  private ToolbarFieldsEditor(Object object, JToolBar jToolBar) {
    ObjectFields.of(object, new Visitor(jToolBar));
  }

  private static JLabel createJLabel(String text) {
    return new JLabel(text);
  }
}
