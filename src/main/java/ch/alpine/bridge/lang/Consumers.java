// code by jph
package ch.alpine.bridge.lang;

import java.util.function.Consumer;

public enum Consumers {
  ;
  public static <T> Consumer<T> empty() {
    return _ -> {
      // ---
    };
  }
  //
  // public static <T> Consumer<T> evoke(Runnable runnable) {
  // return _ -> {
  // runnable.run();
  // };
  // }
}
