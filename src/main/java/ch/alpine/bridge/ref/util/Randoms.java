// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.Color;
import java.time.LocalTime;
import java.util.Random;

/* package */ enum Randoms {
  ;
  public static LocalTime localTime(Random random) {
    return LocalTime.of(random.nextInt(24), random.nextInt(60), random.nextInt(60), random.nextInt(1_000_000_000));
  }

  public static Color color(Random random) {
    return new Color( //
        random.nextInt(256), //
        random.nextInt(256), //
        random.nextInt(256), //
        random.nextInt(256));
  }
}
