// code by jph
package ch.alpine.bridge.os;

import java.nio.file.Path;

import ch.alpine.bridge.swing.LookAndFeels;

enum FallbackOperatingSystem implements OperatingSystem {
  Default;

  @Override
  public LookAndFeels lookAndFeels() {
    return LookAndFeels.LIGHT;
  }

  @Override
  public void navigateTo(Path path) {
  }
}
