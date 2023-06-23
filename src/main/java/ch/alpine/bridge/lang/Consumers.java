// code by jph
package ch.alpine.bridge.lang;

import java.util.function.Consumer;

public enum Consumers {
  ;
  public static final <T> Consumer<T> empty() {
    return s -> {
      // ---
    };
  }
}
