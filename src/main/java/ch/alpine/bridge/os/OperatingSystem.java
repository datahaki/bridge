// code adapted from chatgpt
package ch.alpine.bridge.os;

import ch.alpine.bridge.swing.LookAndFeels;

public interface OperatingSystem {
  public static OperatingSystem get() {
    String OS_STRING = System.getProperty("os.name").toLowerCase();
    if (OS_STRING.contains("nux") || OS_STRING.contains("nix"))
      return LinuxOperatingSystem.Linux;
    // if (OS_STRING.contains("win"))
    // return FallbackOs.INSTANCE;
    // if (OS_STRING.contains("mac"))
    // return FallbackOs.INSTANCE;
    // if (OS_STRING.contains("sunos"))
    return FallbackOperatingSystem.Default;
  }

  LookAndFeels lookAndFeels();
}
