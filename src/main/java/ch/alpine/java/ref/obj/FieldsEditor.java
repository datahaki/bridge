// code by jph
package ch.alpine.java.ref.obj;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.ObjectProperties;
import ch.alpine.java.ref.gui.FieldPanel;
import ch.alpine.java.ref.gui.FieldToolTip;
import ch.alpine.java.ref.gui.ToolbarsComponent;

public class FieldsEditor implements ObjectFieldCallback {
  private final ToolbarsComponent toolbarsComponent = new ToolbarsComponent();
  private final List<FieldPanel> list = new LinkedList<>();
  private final Object object;

  public FieldsEditor(Object object) {
    this.object = object;
    ObjectFieldVisitor.of(this, object);
  }

  @Override
  public void elemental(String prefix, FieldWrap fieldWrap, Object object, Object value) {
    FieldPanel fieldPanel = fieldWrap.createFieldPanel(value);
    list.add(fieldPanel);
    fieldPanel.addListener(string -> ObjectProperties.setIfValid(fieldWrap, object, string));
    JToolBar jToolBar = ToolbarsComponent.createJToolBar(FlowLayout.LEFT);
    JLabel jLabel = new JLabel(prefix);
    jLabel.setToolTipText(FieldToolTip.of(fieldWrap.getField()));
    jLabel.setPreferredSize(new Dimension(jLabel.getPreferredSize().width, 28));
    jToolBar.add(jLabel);
    toolbarsComponent.addPair(jToolBar, fieldPanel.getJComponent(), 28);
  }

  public ToolbarsComponent getToolbarsComponent() {
    return toolbarsComponent;
  }

  public JScrollPane getJScrollPane() {
    return toolbarsComponent.createJScrollPane();
  }

  public List<FieldPanel> list() {
    return Collections.unmodifiableList(list);
  }

  /** @return */
  public JComponent getFieldsAndTextarea() {
    JPanel jPanel;
    jPanel = new JPanel(new BorderLayout());
    jPanel.add("North", getJScrollPane());
    // ---
    JTextArea jTextArea = new JTextArea();
    jTextArea.setBackground(null);
    jTextArea.setEditable(false);
    Consumer<String> consumer = s -> jTextArea.setText(ObjectFieldString.of(object));
    consumer.accept(null);
    list.forEach(d -> d.addListener(consumer));
    jPanel.add("Center", jTextArea);
    return jPanel;
  }

  public void addUniversalListener(Consumer<String> consumer) {
    list.stream().forEach(fieldPanel -> fieldPanel.addListener(consumer));
  }
}
