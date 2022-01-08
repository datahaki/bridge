// code by jph
package ch.alpine.java.ref.util;

import java.awt.BorderLayout;
import java.lang.reflect.Field;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ch.alpine.java.ref.FieldPanel;
import ch.alpine.java.ref.FieldToolTip;
import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.ObjectFieldVisitor;
import ch.alpine.java.ref.ObjectFields;
import ch.alpine.java.ref.ann.FieldLabels;
import ch.alpine.javax.swing.RowPanel;

public class PanelFieldsEditor extends FieldsEditor {
  private class Visitor implements ObjectFieldVisitor {
    private int level = 0;

    @Override // from ObjectFieldVisitor
    public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
      Field field = fieldWrap.getField();
      JLabel jLabel = createJLabel(FieldLabels.of(key, field, null));
      jLabel.setToolTipText(FieldToolTip.of(field));
      FieldPanel createFieldPanel = fieldWrap.createFieldPanel(object, value);
      FieldPanel fieldPanel = register(createFieldPanel, fieldWrap, object);
      rowPanel.appendRow(jLabel, layout(field, fieldPanel.getJComponent()));
    }

    @Override // from ObjectFieldVisitor
    public void push(String key, Field field, Integer index) {
      JLabel jLabel = createJLabel(FieldLabels.of(key, field, index));
      jLabel.setEnabled(false);
      rowPanel.appendRow(jLabel);
      ++level;
    }

    @Override // from ObjectFieldVisitor
    public void pop() {
      --level;
    }

    private JLabel createJLabel(String text) {
      return new JLabel("\u3000".repeat(level) + text);
    }
  }

  private final RowPanel rowPanel = new RowPanel();

  /** @param object */
  public PanelFieldsEditor(Object object) {
    ObjectFields.of(object, new Visitor());
  }

  public JPanel getJPanel() {
    return rowPanel.getJPanel();
  }

  /** @return new instance of scroll panel with panel embedded aligned
   * at the top of the viewport */
  public JScrollPane createJScrollPane() {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(getJPanel(), BorderLayout.NORTH);
    return new JScrollPane(jPanel);
  }

  /** THE USE OF THIS FUNCTION IN THE APPLICATION LAYER IS NOT RECOMMENDED !
   * 
   * @return */
  public RowPanel getRowPanel() {
    return rowPanel;
  }
}
