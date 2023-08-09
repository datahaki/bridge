// code by jph
package ch.alpine.bridge.cal;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ISO8601DateTimeFocusTest {
  @ParameterizedTest
  @EnumSource
  void test(ChronoUnit chronoUnit) {
    ISO8601DateTimeFocus.INSTANCE.focus(chronoUnit);
  }
}
