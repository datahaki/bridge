// code by jph
package ch.alpine.bridge.ref.util;

import java.security.SecureRandom;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Test;

class RandomsTest {
  @Test
  void test() {
    RandomGenerator randomGenerator = new SecureRandom();
    Randoms.localTime(randomGenerator);
    Randoms.color(randomGenerator);
    Randoms.font(randomGenerator);
  }
}
