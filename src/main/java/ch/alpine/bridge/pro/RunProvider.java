// code by jph
package ch.alpine.bridge.pro;

public sealed interface RunProvider permits //
    ManipulateProvider, //
    WindowProvider, //
    ShowProvider, //
    VoidProvider {
  /** @return */
  Object runStandalone();
}
