// code adapted from chatgpt
package ch.alpine.bridge.io;

public enum SanitizeFilename {
  ;
  /** @param string
   * @return */
  public static String of(String string) {
    return string //
        .replaceAll("[\\\\/:*?\"<>|]", "_") //
        .replaceAll("\\s+$", "") //
        .replaceAll("\\.+$", "");
  }
}
