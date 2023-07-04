// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalTime;
import java.util.List;
import java.util.random.RandomGenerator;

import ch.alpine.bridge.swing.FontDialog;
import ch.alpine.bridge.swing.FontStyle;

/* package */ enum Randoms {
  ;
  public static LocalTime localTime(RandomGenerator randomGenerator) {
    return LocalTime.of(randomGenerator.nextInt(24), randomGenerator.nextInt(60), randomGenerator.nextInt(60), randomGenerator.nextInt(1_000_000_000));
  }

  public static Color color(RandomGenerator randomGenerator) {
    return new Color( //
        randomGenerator.nextInt(256), //
        randomGenerator.nextInt(256), //
        randomGenerator.nextInt(256), //
        randomGenerator.nextInt(256));
  }

  public static Font font(RandomGenerator randomGenerator) {
    List<String> list = FontDialog.FontParam.names();
    return new Font( //
        list.get(randomGenerator.nextInt(list.size())), //
        randomGenerator.nextInt(FontStyle.values().length), //
        randomGenerator.nextInt(40));
  }
}
