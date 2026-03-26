// code by jph
package ch.alpine.bridge.io;

import java.util.logging.Level;

import ch.alpine.tensor.ext.UserName;

public enum GitHubCI {
  ;
  private static final boolean isGitHubCI = UserName.whoami().startsWith("runner");

  public static void println(Object object) {
    if (isGitHubCI)
      IO.println(" [" + Level.INFO + "] " + object);
  }

  public static boolean isRunner() {
    return isGitHubCI;
  }
}
