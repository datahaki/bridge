// code by jph
package ch.alpine.bridge.os;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

import ch.alpine.bridge.swing.LookAndFeels;

/** @param file intended to open in file navigator/explorer window while selected */
enum WindowsOperatingSystem implements OperatingSystem {
  Windows;

  @Override
  public LookAndFeels lookAndFeels() {
    return LookAndFeels.LIGHT;
  }

  @Override
  public void navigateTo(Path path) {
    try {
      new ProcessBuilder("explorer.exe", "/select,\"" + path + "\"").start();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
