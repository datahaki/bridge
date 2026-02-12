// code by jph
package ch.alpine.bridge.os;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

import ch.alpine.bridge.swing.LookAndFeels;

enum LinuxOperatingSystem implements OperatingSystem {
  Linux;

  @Override
  public LookAndFeels lookAndFeels() {
    try {
      Process process = Runtime.getRuntime().exec(new String[] { //
          "gsettings", "get", "org.gnome.desktop.interface", "color-scheme" });
      process.waitFor();
      String output = null;
      try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        output = bufferedReader.readLine();
      }
      if (Objects.nonNull(output)) {
        // 'default'
        if (output.equals("'prefer-dark'"))
          return LookAndFeels.DARK;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return LookAndFeels.LIGHT;
  }
}
