// code by jph
package ch.alpine.bridge.swing;

@FunctionalInterface
public interface SpinnerListener<T> {
  /** @param type */
  void actionPerformed(T type);
}
