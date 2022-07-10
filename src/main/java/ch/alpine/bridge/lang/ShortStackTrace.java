// code by jph
package ch.alpine.bridge.lang;

import java.util.Arrays;
import java.util.Optional;

public class ShortStackTrace {
  private final String[] prefix;

  public ShortStackTrace(String... prefix) {
    this.prefix = prefix;
  }

  public void print(Exception exception) {
    StackTraceElement[] stackTraceElements = exception.getStackTrace();
    for (StackTraceElement stackTraceElement : stackTraceElements) {
      String className = stackTraceElement.getClassName();
      Optional<String> optional = Arrays.stream(prefix).filter(className::startsWith).findAny();
      if (optional.isPresent())
        System.err.println(" " + stackTraceElement.toString());
    }
  }
}
