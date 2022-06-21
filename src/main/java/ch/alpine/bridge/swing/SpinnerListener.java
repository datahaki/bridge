// code by jph
package ch.alpine.bridge.swing;

@FunctionalInterface
public interface SpinnerListener<T> {
  /** function is invoked by {@link SpinnerLabel} and {@link SpinnerMenu}
   * when the selected value was changed through user interaction
   * 
   * specifically, the function is <em>not</em> invoked by
   * {@link SpinnerLabel#setValue(Object)}
   * 
   * @param value selected */
  void spun(T value);
}
