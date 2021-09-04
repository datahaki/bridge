// code by jph
package ch.alpine.java.ref.obj;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JToolBar;

import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.ObjectProperties;
import ch.alpine.java.ref.gui.FieldPanel;
import ch.alpine.java.ref.gui.FieldToolTip;
import ch.alpine.java.ref.gui.ToolbarsComponent;

public class FieldEditor implements ObjectFieldCallback {
  private final ToolbarsComponent toolbarsComponent = new ToolbarsComponent();

  public FieldEditor(Object object) {
    ObjectFieldVisitor.of(this, object);
  }

  @Override
  public void elemental(String prefix, FieldWrap fieldWrap, Object object, Object value) {
    FieldPanel fieldPanel = fieldWrap.createFieldPanel(value);
    fieldPanel.addListener(string -> ObjectProperties.setIfValid(fieldWrap, object, string));
    JToolBar jToolBar = ToolbarsComponent.createJToolBar(FlowLayout.LEFT);
    JLabel jLabel = new JLabel(prefix);
    jLabel.setToolTipText(FieldToolTip.of(fieldWrap.getField()));
    jLabel.setPreferredSize(new Dimension(jLabel.getPreferredSize().width, 28));
    jToolBar.add(jLabel);
    toolbarsComponent.addPair(jToolBar, fieldPanel.getJComponent(), 28);
  }

  public ToolbarsComponent toolbarsComponent() {
    return toolbarsComponent;
  }
}
