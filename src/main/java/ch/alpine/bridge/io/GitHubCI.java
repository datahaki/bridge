// code by jph
package ch.alpine.bridge.io;

import java.util.logging.Level;

import ch.alpine.tensor.ext.UserName;

public enum GitHubCI {
  INFO(Level.INFO, AnsiColor.GREEN),
  SEVERE(Level.SEVERE, AnsiColor.RED),
  //
  ;

  private static final boolean isGitHubCI = UserName.whoami().startsWith("runner");
  // ---
  // private final Level level;
  // private final AnsiColor ansiColor;
  private final String bracket;

  private GitHubCI(Level level, AnsiColor ansiColor) {
    // this.level = level;
    // this.ansiColor = ansiColor;
    bracket = " [" + ansiColor.wrap(level.getName()) + "] ";
  }

  public void println(Object object) {
    if (isGitHubCI)
      IO.println(bracket + object);
  }

  public static boolean isRunner() {
    return isGitHubCI;
  }
}
