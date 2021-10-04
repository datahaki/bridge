// code by jph
package ch.alpine.java.ref.gui;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JLabel;
import javax.swing.JToolBar;

import ch.alpine.java.ref.FieldPanel;
import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.ObjectFieldVisitor;
import ch.alpine.java.ref.ObjectFields;
import ch.alpine.java.ref.ann.FieldLabels;

public class FieldsToolbar {
  private static final int HEIGHT = 28;

  private class Visitor implements ObjectFieldVisitor {
    @Override // from ObjectFieldVisitor
    public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
      FieldPanel fieldPanel = fieldWrap.createFieldPanel(value);
      list.add(fieldPanel);
      fieldPanel.addListener(string -> fieldWrap.setIfValid(object, string));
      JLabel jLabel = createJLabel(FieldLabels.of(key, fieldWrap.getField(), null));
      jLabel.setToolTipText(FieldToolTip.of(fieldWrap.getField()));
      int width = jLabel.getPreferredSize().width;
      jLabel.setPreferredSize(new Dimension(width, HEIGHT));
      jToolBar.add(jLabel);
      jToolBar.add(fieldPanel.getJComponent());
      jToolBar.addSeparator();
    }

    @Override // from ObjectFieldVisitor
    public void push(String key, Field field, Integer index) {
      JLabel jLabel = createJLabel(FieldLabels.of(key, field, index));
      jLabel.setEnabled(false);
      jToolBar.add(jLabel);
    }

    @Override // from ObjectFieldVisitor
    public void pop() {
      jToolBar.addSeparator();
    }
  }

  // ---
  private final List<FieldPanel> list = new LinkedList<>();
  private final JToolBar jToolBar;

  /** @param object */
  public FieldsToolbar(Object object, JToolBar jToolBar) {
    this.jToolBar = jToolBar;
    ObjectFields.of(object, new Visitor());
  }

  public List<FieldPanel> list() {
    return Collections.unmodifiableList(list);
  }

  /** @param runnable that will be run if any value in editor was subject to change */
  public void addUniversalListener(Runnable runnable) {
    Consumer<String> consumer = string -> runnable.run();
    list.forEach(fieldPanel -> fieldPanel.addListener(consumer));
  }

  private static JLabel createJLabel(String text) {
    return new JLabel(text);
  }
}
