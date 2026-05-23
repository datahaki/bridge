// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;

import javax.swing.JComponent;

interface PanelBuilder {
  /** @param jComponent */
  void append(JComponent jComponent);

  /** @param key
   * @param field
   * @param index */
  void push(String key, Field field, Integer index);

  /** @param key
   * @param field
   * @param jComponent2 */
  void item(String key, Field field, JComponent jComponent2);

  /**
   * 
   */
  void pop();

  /** @return */
  JComponent getJComponent();
}
