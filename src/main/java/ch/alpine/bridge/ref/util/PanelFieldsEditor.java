// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.BorderLayout;
import java.lang.reflect.Field;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ch.alpine.bridge.ref.FieldPanel;
import ch.alpine.bridge.ref.FieldToolTip;
import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ObjectFieldAll;
import ch.alpine.bridge.ref.ObjectFields;
import ch.alpine.bridge.ref.ann.FieldLabels;
import ch.alpine.bridge.swing.RowPanelBuilder;

public class PanelFieldsEditor extends FieldsEditor {
  private class Visitor extends ObjectFieldAll {
    private int level = 0;

    @Override // from ObjectFieldVisitor
    public void push(String key, Field field, Integer index) {
      System.out.println("HERE " + key);
      JLabel jLabel = createJLabel(FieldLabels.of(key, field, index));
      jLabel.setEnabled(false);
      rowPanelBuilder.appendRow(jLabel);
      ++level;
    }

    @Override // from ObjectFieldVisitor
    public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
      Field field = fieldWrap.getField();
      JLabel jLabel = createJLabel(FieldLabels.of(key, field, null));
      jLabel.setToolTipText(FieldToolTip.of(field));
      FieldPanel createFieldPanel = fieldWrap.createFieldPanel(object, value);
      FieldPanel fieldPanel = register(createFieldPanel, fieldWrap, object);
      rowPanelBuilder.appendRow(jLabel, layout(field, fieldPanel.getJComponent()));
    }

    @Override // from ObjectFieldVisitor
    public void pop() {
      --level;
    }

    private JLabel createJLabel(String text) {
      return new JLabel("\u3000".repeat(level) + text);
    }
  }

  private final RowPanelBuilder rowPanelBuilder = new RowPanelBuilder();

  /** @param object */
  public PanelFieldsEditor(Object object) {
    ObjectFields.of(object, new Visitor());
  }

  public JPanel getJPanel() {
    return rowPanelBuilder.getJPanel();
  }

  /** @return new instance of scroll panel with panel embedded aligned
   * at the top of the viewport */
  public JScrollPane createJScrollPane() {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(getJPanel(), BorderLayout.NORTH);
    return new JScrollPane(jPanel);
  }
}
