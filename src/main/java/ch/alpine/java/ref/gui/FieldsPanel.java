// code by jph
package ch.alpine.java.ref.gui;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import ch.alpine.java.ref.FieldPanel;
import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.ObjectFieldVisitor;
import ch.alpine.java.ref.ObjectFields;
import ch.alpine.java.ref.ann.FieldLabels;

public class FieldsPanel implements FieldsEditor {
  private static final int HEIGHT = 28;

  private class Visitor implements ObjectFieldVisitor {
    private int level = 0;

    @Override // from ObjectFieldVisitor
    public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
      FieldPanel fieldPanel = fieldWrap.createFieldPanel(value);
      list.add(fieldPanel);
      fieldPanel.addListener(string -> fieldWrap.setIfValid(object, string));
      JLabel jLabel = createJLabel(FieldLabels.of(key, fieldWrap.getField(), null));
      jLabel.setToolTipText(FieldToolTip.of(fieldWrap.getField()));
      int width = jLabel.getPreferredSize().width;
      jLabel.setPreferredSize(new Dimension(width, HEIGHT));
      rowPanel.appendRow(jLabel, fieldPanel.getJComponent(), HEIGHT);
    }

    @Override // from ObjectFieldVisitor
    public void push(String key, Field field, Integer index) {
      JLabel jLabel = createJLabel(FieldLabels.of(key, field, index));
      jLabel.setEnabled(false);
      rowPanel.appendRow(jLabel, 20);
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
  private final List<FieldPanel> list = new LinkedList<>();
  private final JScrollPane jScrollPane;

  /** @param object */
  public FieldsPanel(Object object) {
    ObjectFields.of(object, new Visitor());
    jScrollPane = rowPanel.createJScrollPane();
  }

  @Override
  public List<FieldPanel> list() {
    return Collections.unmodifiableList(list);
  }

  @Override
  public void addUniversalListener(Runnable runnable) {
    Consumer<String> consumer = string -> runnable.run();
    list.forEach(fieldPanel -> fieldPanel.addListener(consumer));
  }

  public JScrollPane getJScrollPane() {
    return jScrollPane;
  }
}
