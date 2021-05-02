// code by jph
package ch.alpine.java.awt;

@FunctionalInterface
public interface SpinnerListener<T> {
  /** @param type */
  void actionPerformed(T type);
}
