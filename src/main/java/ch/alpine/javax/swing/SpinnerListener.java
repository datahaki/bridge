// code by jph
package ch.alpine.javax.swing;

@FunctionalInterface
public interface SpinnerListener<T> {
  /** @param type */
  void actionPerformed(T type);
}
