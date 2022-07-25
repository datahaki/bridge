// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import ch.alpine.bridge.swing.FontDialog;
import ch.alpine.bridge.swing.FontStyle;

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

  public static Font font(Random random) {
    List<String> list = FontDialog.FontParam.names();
    return new Font( //
        list.get(random.nextInt(list.size())), //
        random.nextInt(FontStyle.values().length), //
        random.nextInt(40));
  }
}
