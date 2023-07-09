// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.BorderLayout;
import java.lang.reflect.Field;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ch.alpine.bridge.ref.FieldPanel;
import ch.alpine.bridge.ref.FieldWrap;

public class PanelFieldsEditor extends FieldsEditor {
  /** @param object
   * @return */
  public static PanelFieldsEditor nested(Object object) {
    return new PanelFieldsEditor(object, new Rec2PanelBuilder());
  }

  /** @param object
   * @return */
  public static PanelFieldsEditor splits(Object object) {
    return new PanelFieldsEditor(object, new Col2PanelBuilder());
  }

  /** @param object
   * @return */
  public static PanelFieldsEditor single(Object object) {
    return new PanelFieldsEditor(object, new Col1PanelBuilder());
  }

  // ---
  private class Visitor extends ObjectFieldAll {
    @Override // from ObjectFieldVisitor
    public void push(String key, Field field, Integer index) {
      panelBuilder.push(key, field, index);
    }

    @Override // from ObjectFieldVisitor
    public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
      FieldPanel fieldPanel = fieldWrap.createFieldPanel(object, value);
      register(fieldPanel, object);
      panelBuilder.item(key, fieldWrap.getField(), fieldPanel.getJComponent());
    }

    @Override // from ObjectFieldVisitor
    public void pop() {
      panelBuilder.pop();
    }
  }

  private final PanelBuilder panelBuilder;

  private PanelFieldsEditor(Object object, PanelBuilder panelBuilder) {
    this.panelBuilder = panelBuilder;
    ObjectFields.of(object, new Visitor());
  }

  public JComponent getJPanel() {
    return panelBuilder.getJComponent();
  }

  /** @return new instance of scroll panel with panel embedded aligned
   * at the top of the viewport */
  public JScrollPane createJScrollPane() {
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(getJPanel(), BorderLayout.NORTH);
    return new JScrollPane(jPanel);
  }
}
