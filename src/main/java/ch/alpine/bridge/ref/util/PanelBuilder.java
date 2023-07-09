// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;

import javax.swing.JComponent;

interface PanelBuilder {
  void append(JComponent jComponent);

  void push(String key, Field field, Integer index);

  void item(String key, Field field, JComponent jComponent2);

  void pop();

  JComponent getJComponent();
}
