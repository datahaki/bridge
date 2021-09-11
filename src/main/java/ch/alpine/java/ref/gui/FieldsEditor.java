// code by jph
package ch.alpine.java.ref.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.lang.reflect.Field;
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

import ch.alpine.java.ref.FieldPanel;
import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.ObjectFieldVisitor;
import ch.alpine.java.ref.ObjectFields;
import ch.alpine.java.ref.ObjectProperties;
import ch.alpine.java.ref.ann.FieldLabels;

public class FieldsEditor implements ObjectFieldVisitor {
  // TODO 20210909 temporary solution to experiment with different platforms
  public static int PADDING = 12;
  private static final int HEIGHT = 28;
  // ---
  private final ToolbarsComponent toolbarsComponent = new ToolbarsComponent();
  private final List<FieldPanel> list = new LinkedList<>();
  private final Object object;
  private final JScrollPane jScrollPane;
  private int level = 0;

  /** @param object */
  public FieldsEditor(Object object) {
    this.object = object;
    ObjectFields.of(object, this);
    jScrollPane = toolbarsComponent.createJScrollPane();
  }

  @Override // from ObjectFieldVisitor
  public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
    FieldPanel fieldPanel = fieldWrap.createFieldPanel(value);
    list.add(fieldPanel);
    fieldPanel.addListener(string -> fieldWrap.setIfValid(object, string));
    JToolBar jToolBar = ToolbarsComponent.createJToolBar(FlowLayout.LEFT);
    {
      JLabel jLabel = createJLabel(FieldLabels.of(key, fieldWrap.getField(), null));
      jLabel.setToolTipText(FieldToolTip.of(fieldWrap.getField()));
      int width = jLabel.getPreferredSize().width + PADDING;
      jLabel.setPreferredSize(new Dimension(width, HEIGHT));
      jToolBar.add(jLabel);
    }
    toolbarsComponent.addPair(jToolBar, fieldPanel.getJComponent(), HEIGHT);
  }

  @Override // from ObjectFieldVisitor
  public void push(String key, Field field, Integer index) {
    JLabel jLabel = createJLabel(FieldLabels.of(key, field, index));
    jLabel.setEnabled(false);
    toolbarsComponent.addPair(jLabel, new JLabel(), 20);
    ++level;
  }

  @Override // from ObjectFieldVisitor
  public void pop() {
    --level;
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

  /** @param runnable that will be run if any value in editor was subject to change */
  public void addUniversalListener(Runnable runnable) {
    Consumer<String> consumer = string -> runnable.run();
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
    list.forEach(fieldPanel -> fieldPanel.addListener(consumer));
    jPanel.add("Center", jTextArea);
    return jPanel;
  }

  private JLabel createJLabel(String text) {
    return new JLabel("\u3000".repeat(level) + text);
  }
}
