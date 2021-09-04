// code by jph
package ch.alpine.java.ref.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
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
import ch.alpine.java.ref.ObjectFieldVisitor;
import ch.alpine.java.ref.ObjectFields;
import ch.alpine.java.ref.ObjectProperties;

public class FieldsEditor implements ObjectFieldVisitor {
  private final ToolbarsComponent toolbarsComponent = new ToolbarsComponent();
  private final List<FieldPanel> list = new LinkedList<>();
  private final Object object;
  private final JScrollPane jScrollPane;
  private int level = 0;

  public FieldsEditor(Object object) {
    this.object = object;
    ObjectFields.of(object, this);
    jScrollPane = toolbarsComponent.createJScrollPane();
  }

  @Override
  public void accept(String prefix, FieldWrap fieldWrap, Object object, Object value) {
    FieldPanel fieldPanel = fieldWrap.createFieldPanel(value);
    list.add(fieldPanel);
    fieldPanel.addListener(string -> fieldWrap.setIfValid(object, string));
    JToolBar jToolBar = ToolbarsComponent.createJToolBar(FlowLayout.LEFT);
    {
      JLabel jLabel = createJLabel(prefix);
      jLabel.setToolTipText(FieldToolTip.of(fieldWrap.getField()));
      jLabel.setPreferredSize(new Dimension(jLabel.getPreferredSize().width, 28));
      jToolBar.add(jLabel);
    }
    toolbarsComponent.addPair(jToolBar, fieldPanel.getJComponent(), 28);
  }

  @Override
  public void push(String prefix) {
    JLabel jLabel = createJLabel(prefix);
    ++level;
    jLabel.setForeground(Color.GRAY);
    toolbarsComponent.addPair(jLabel, new JComponent() {
      @Override
      protected void paintComponent(Graphics g) {
        Dimension dimension = getSize();
        g.setColor(Color.LIGHT_GRAY);
        int piy = dimension.height / 2;
        g.drawLine(0, piy, dimension.width, piy);
      }
    }, 20);
  }

  @Override
  public void pop() {
    --level;
  }

  private JLabel createJLabel(String prefix) {
    return new JLabel("\u3000".repeat(level) + prefix.substring(prefix.lastIndexOf('.') + 1));
  }

  public ToolbarsComponent getToolbarsComponent() {
    return toolbarsComponent;
  }

  public JScrollPane getJScrollPane() {
    return jScrollPane;
  }

  public List<FieldPanel> list() {
    return Collections.unmodifiableList(list);
  }

  public void addUniversalListener(Consumer<String> consumer) {
    list.stream().forEach(fieldPanel -> fieldPanel.addListener(consumer));
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
    Consumer<String> consumer = s -> jTextArea.setText(ObjectProperties.string(object));
    consumer.accept(null);
    list.forEach(d -> d.addListener(consumer));
    jPanel.add("Center", jTextArea);
    return jPanel;
  }
}
