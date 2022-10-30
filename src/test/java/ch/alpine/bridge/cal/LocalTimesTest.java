// code by all
package ch.alpine.bridge.cal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class LocalTimesTest {
  @Test
  void test() {
    assertEquals(LocalTimes._30_S.floor(LocalTime.of(13, 14, 18)), LocalTime.of(13, 14, 0));
    assertEquals(LocalTimes._30_S.floor(LocalTime.of(13, 14, 38)), LocalTime.of(13, 14, 30));
    assertEquals(LocalTimes._01_MIN.floor(LocalTime.of(13, 34, 38)), LocalTime.of(13, 34));
    assertEquals(LocalTimes._20_MIN.floor(LocalTime.of(13, 34, 38)), LocalTime.of(13, 20));
    assertEquals(LocalTimes._20_MIN.floor(LocalTime.of(13, 47, 38)), LocalTime.of(13, 40));
    assertEquals(LocalTimes._1_H.floor(LocalTime.of(13, 47, 38)), LocalTime.of(13, 00));
    assertEquals(LocalTimes._6_H.floor(LocalTime.of(13, 47, 38)), LocalTime.of(12, 00));
  }

  @Test
  void testDifferent() {
    LocalTime localTime = LocalTime.of(23, 59, 59, 999_999_999);
    long count = Stream.of(LocalTimes.values()).map(s -> s.floor(localTime)).distinct().count();
    assertEquals(count, LocalTimes.values().length);
  }
}
