// code by jph
package ch.alpine.bridge.gfx;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.time.LocalTime;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Test;

class LocalTimeDisplayTest {
  public static LocalTime localTime(RandomGenerator randomGenerator) {
    return LocalTime.of( //
        randomGenerator.nextInt(24), // hrs
        randomGenerator.nextInt(60), // min
        randomGenerator.nextInt(60), // sec
        randomGenerator.nextInt(1_000_000_000)); // nanos
  }

  @Test
  void test() {
    BufferedImage bufferedImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    LocalTime localTime = localTime(new SecureRandom());
    LocalTimeDisplay.draw(graphics, localTime, new Point(150, 150));
  }
}
