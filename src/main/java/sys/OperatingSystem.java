// code by jph
package sys;

import java.util.Locale;

public enum OperatingSystem {
  LINUX,
  WINDOWS,
  MAC_OS,
  UNKNOWN;

  /** http://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
   * 
   * @return */
  private static OperatingSystem establish() {
    String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
    if ((OS.contains("mac")) || (OS.contains("darwin"))) {
      return MAC_OS;
    } else //
    if (OS.contains("win")) {
      return WINDOWS;
    } else //
    if (OS.contains("nux")) {
      return LINUX;
    }
    return UNKNOWN;
  }

  // TODO MIDI access via function
  public static final OperatingSystem TYPE = establish();

  public static boolean isWindows() {
    return TYPE.equals(WINDOWS);
  }

  public static boolean isLinux() {
    return TYPE.equals(LINUX);
  }

  public static String getExecutable(String string) {
    switch (TYPE) {
    case WINDOWS:
      return string + ".exe";
    default:
      return string;
    }
  }
}
