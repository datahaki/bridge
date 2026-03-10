// code by jph
package ch.alpine.bridge.pro;

/** computation that has no graphical user interface, for instance
 * motion planning task
 * machine learning */
public non-sealed interface VoidProvider extends RunProvider {
  /** @return */
  @Override
  Void runStandalone();
}
