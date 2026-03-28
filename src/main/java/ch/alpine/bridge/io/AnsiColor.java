// code by jph
package ch.alpine.bridge.io;

public enum AnsiColor {
  NEUTRAL(null) {
    @Override
    public String wrap(String string) {
      return string;
    }
  },
  RED("\u001B[31m"),
  GREEN("\u001B[32m"),
  YELLOW("\u001B[33m"),
  BLUE("\u001B[34m"),
  PURPLE("\u001B[35m"),
  CYAN("\u001B[36m"),
  BLACK("\u001B[30m"),
  WHITE("\u001B[37m");

  private static final String RESET = "\u001B[0m";
  private final String ansi;

  private AnsiColor(String ansi) {
    this.ansi = ansi;
  }

  public String wrap(String string) {
    return ansi + string + RESET;
  }
}
