// code by jph
package ch.alpine.bridge.lang;

import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;
import java.util.Arrays;
import java.util.function.Predicate;

public class ShortStackTrace {
  private final Predicate<StackFrame> predicate;

  public ShortStackTrace(String... prefix) {
    predicate = stackFrame -> Arrays.stream(prefix).filter(stackFrame.getClassName()::startsWith).findAny().isPresent();
  }

  public void print() {
    StackWalker stackWalker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
    stackWalker.walk(stream -> stream //
        .skip(1) // skip stackwalker
        .filter(predicate) //
        .peek(IO::println) //
        .count());
  }
}
