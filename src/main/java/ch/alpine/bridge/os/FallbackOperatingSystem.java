// code by jph
package ch.alpine.bridge.os;

import ch.alpine.bridge.swing.LookAndFeels;

enum FallbackOperatingSystem implements OperatingSystem {
  Default;

  @Override
  public LookAndFeels lookAndFeels() {
    return LookAndFeels.LIGHT;
  }
}
