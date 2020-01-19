// code by jph
package ch.ethz.idsc.java.awt;

@FunctionalInterface
public interface SpinnerListener<T> {
  /** @param type */
  void actionPerformed(T type);
}
