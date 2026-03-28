// code by jph
package ch.alpine.bridge.io;

public enum AnsiStyle {
  BOLD("\033[1m"),
  DIM("\033[2m"),
  UNDERLINE("\033[4m"),;

  private static final String RESET = "\033[0m";
  private final String ansi;

  AnsiStyle(String ansi) {
    this.ansi = ansi;
  }

  public String wrap(String string) {
    return ansi + string + RESET;
  }
}
