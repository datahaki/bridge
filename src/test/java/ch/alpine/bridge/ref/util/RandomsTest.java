// code by jph
package ch.alpine.bridge.ref.util;

import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Test;

class RandomsTest {
  @Test
  void test() {
    RandomGenerator randomGenerator = ThreadLocalRandom.current();
    Randoms.localTime(randomGenerator);
    Randoms.color(randomGenerator);
    Randoms.font(randomGenerator);
  }
}
