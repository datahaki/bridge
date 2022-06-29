// code by jph
package ch.alpine.bridge.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.RepeatedTest;

class LocalTimeParamTest {
  @RepeatedTest(10)
  void test() {
    LocalTime localTime = LocalTime.now();
    LocalTimeParam localTimeParam = new LocalTimeParam(localTime);
    LocalTime comp = localTimeParam.toLocalTime();
    assertEquals(localTime, comp);
  }
}
