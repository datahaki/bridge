// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.Font;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import ch.alpine.bridge.ref.FieldsEditorParam;
import ch.alpine.bridge.ref.ann.FieldLabels;

/* package */ final class Rec2PanelBuilder implements PanelBuilder {
  private final Deque<PanelBuilder> deque = new ArrayDeque<>();

  public Rec2PanelBuilder() {
    deque.push(new Col2PanelBuilder());
  }

  @Override
  public void append(JComponent jComponent) {
    throw new IllegalStateException();
  }

  /** @param jComponent that spreads over the entire width */
  @Override
  public void push(String key, Field field, Integer index) {
    JLabel jLabel = FieldsEditorParam.GLOBAL.createLabel(FieldLabels.of(key, field, index));
    jLabel.setFont(jLabel.getFont().deriveFont(Font.BOLD));
    PanelBuilder panelBuilder = new Col2PanelBuilder();
    JComponent jComponent = panelBuilder.getJComponent();
    jComponent.setBorder(new EmptyBorder(0, FieldsEditorParam.INSET_LEFT, 0, 0));
    deque.peek().append(jLabel);
    deque.peek().append(jComponent);
    deque.push(panelBuilder);
  }

  /** append a row consisting of two components of equal height:
   * jComponent1 is shown to the left, and jComponent2 to the right
   * 
   * @param jComponent1
   * @param jComponent2 */
  @Override
  public void item(String key, Field field, JComponent jComponent2) {
    deque.peek().item(key, field, jComponent2);
  }

  @Override
  public void pop() {
    deque.pop();
  }

  @Override
  public JComponent getJComponent() {
    return deque.peek().getJComponent();
  }
}
