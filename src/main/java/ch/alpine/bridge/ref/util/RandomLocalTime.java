// code by jph
package ch.alpine.bridge.ref.util;

import java.time.LocalTime;
import java.util.Random;

/* package */ enum RandomLocalTime {
  ;
  public static LocalTime of(Random random) {
    return LocalTime.of(random.nextInt(24), random.nextInt(60), random.nextInt(60), random.nextInt(1_000_000_000));
  }
}
