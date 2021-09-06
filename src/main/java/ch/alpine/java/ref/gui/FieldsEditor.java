// code by jph
package ch.alpine.java.ref.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import ch.alpine.java.ref.FieldLabel;
import ch.alpine.java.ref.FieldLabels;
import ch.alpine.java.ref.FieldPanel;
import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.ObjectFieldVisitor;
import ch.alpine.java.ref.ObjectFields;
import ch.alpine.java.ref.ObjectProperties;

public class FieldsEditor implements ObjectFieldVisitor {
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
      JLabel jLabel = createJLabel(text(key, fieldWrap.getField(), null));
      jLabel.setToolTipText(FieldToolTip.of(fieldWrap.getField()));
      jLabel.setPreferredSize(new Dimension(jLabel.getPreferredSize().width, HEIGHT));
      jToolBar.add(jLabel);
    }
    toolbarsComponent.addPair(jToolBar, fieldPanel.getJComponent(), HEIGHT);
  }

  @Override // from ObjectFieldVisitor
  public void push(String key, Field field, Integer index) {
    JLabel jLabel = createJLabel(text(key, field, index));
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

  // ==================================================
  /** @param key
   * @param field
   * @param index non-null iff field represents array, or list
   * @return */
  private static String text(String key, Field field, Integer index) {
    {
      FieldLabel fieldLabel = field.getAnnotation(FieldLabel.class);
      if (Objects.isNull(index)) // base case
        return Objects.isNull(fieldLabel) //
            ? key.substring(key.lastIndexOf('.') + 1) // default choice: label text as in java code
            : fieldLabel.text();
      // ---
      // below here, index is guaranteed to be non-null
      if (Objects.nonNull(fieldLabel))
        try {
          // we make the assumption that the user wants to see the counter starting from 1
          return String.format(fieldLabel.text(), index + 1);
        } catch (Exception exception) {
          // in case field label is not suitable for use with String#format
        }
    }
    // ---
    {
      FieldLabels fieldLabels = field.getAnnotation(FieldLabels.class);
      if (Objects.nonNull(fieldLabels)) {
        String[] text = fieldLabels.text();
        if (index < text.length)
          return text[index];
      }
    }
    return key.substring(key.lastIndexOf('.') + 1);
  }

  private JLabel createJLabel(String text) {
    return new JLabel("\u3000".repeat(level) + text);
  }
}
