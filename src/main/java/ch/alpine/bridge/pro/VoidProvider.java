// code by jph
package ch.alpine.bridge.pro;

public non-sealed interface VoidProvider extends RunProvider {
  /** @return */
  @Override
  Void runStandalone();
}
