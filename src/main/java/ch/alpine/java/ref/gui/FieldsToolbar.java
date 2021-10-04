// code by jph
package ch.alpine.java.ref.gui;

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

public class FieldsToolbar implements FieldsEditor {
  /** @param object
   * @param jToolBar */
  public static FieldsToolbar add(Object object, JToolBar jToolBar) {
    return new FieldsToolbar(object, jToolBar);
  }

  private class Visitor implements ObjectFieldVisitor {
    private final JToolBar jToolBar;

    public Visitor(JToolBar jToolBar) {
      this.jToolBar = jToolBar;
    }

    @Override // from ObjectFieldVisitor
    public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
      FieldPanel fieldPanel = fieldWrap.createFieldPanel(value);
      list.add(fieldPanel);
      fieldPanel.addListener(string -> fieldWrap.setIfValid(object, string));
      JLabel jLabel = createJLabel(FieldLabels.of(key, fieldWrap.getField(), null));
      jLabel.setToolTipText(FieldToolTip.of(fieldWrap.getField()));
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

  private final List<FieldPanel> list = new LinkedList<>();

  private FieldsToolbar(Object object, JToolBar jToolBar) {
    ObjectFields.of(object, new Visitor(jToolBar));
  }

  @Override
  public List<FieldPanel> list() {
    return Collections.unmodifiableList(list);
  }

  /** @param runnable that will be run if any value in editor was subject to change */
  @Override
  public void addUniversalListener(Runnable runnable) {
    Consumer<String> consumer = string -> runnable.run();
    list.forEach(fieldPanel -> fieldPanel.addListener(consumer));
  }

  private static JLabel createJLabel(String text) {
    return new JLabel(text);
  }
}
