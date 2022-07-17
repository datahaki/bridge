// code auto generated, concept by gjoel
package ch.alpine.bridge.swing;

import java.util.function.LongSupplier;

import javax.swing.UIManager;

public enum UIManagerLong implements LongSupplier {
  ComboBox_timeFactor,
  List_timeFactor,
  Tree_timeFactor;

  @Override
  public long getAsLong() {
    return (long) UIManager.get(key());
  }

  public String key() {
    return name().replace('_', '.');
  }
}
