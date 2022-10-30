// code by all
package ch.alpine.bridge.cal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.OrderedQ;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Sign;

class DateTimeIntervalTest {
  private static final Scalar ONE_SEC = Quantity.of(1, "s");

  @Test
  void addToTest1() {
    DateTime now = DateTime.now();
    Scalar after1 = now.add(ONE_SEC);
    // before must before now
    assertTrue(Scalars.lessThan(now, after1));
    // difference must be 1 second
    Scalar timePassed = after1.subtract(now);
    assertEquals(ONE_SEC, timePassed);
  }

  @Test
  void addToTest2() {
    DateTime now = DateTime.now();
    Scalar before1 = now.subtract(ONE_SEC);
    // before must before now
    assertTrue(Scalars.lessThan(before1, now));
    // difference must be 1 second
    Scalar timePassed = now.subtract(before1);
    // System.out.println(timePassed);
    assertEquals(ONE_SEC, timePassed);
  }

  @Test
  void floor() {
    assertEquals(DateTimeInterval._1_DAY.floor(LocalDateTime.of(2022, 12, 13, 14, 15, 16)), LocalDateTime.of(2022, 12, 13, 0, 0));
    assertEquals(DateTimeInterval._1_WK.floor(LocalDateTime.of(2022, 9, 29, 14, 15, 16)), LocalDateTime.of(2022, 9, 26, 0, 0));
  }

  @Test
  void diffUnique() {
    LocalDateTime now = LocalDateTime.now();
    Set<Scalar> set = new HashSet<>();
    for (DateTimeInterval dateTimeIntervals : DateTimeInterval.values()) {
      LocalDateTime min = dateTimeIntervals.floor(now);
      LocalDateTime max = dateTimeIntervals.plus(min);
      DateTime.of(max).subtract(DateTime.of(min));
      set.add(dateTimeIntervals.durationHint());
      Sign.requirePositive(dateTimeIntervals.durationHint());
    }
    assertEquals(set.size(), DateTimeInterval.values().length);
  }

  @Test
  void duractionIncreasting() {
    Tensor tensor = Tensor.of(Stream.of(DateTimeInterval.values()).map(DateTimeInterval::durationHint).distinct());
    assertTrue(OrderedQ.of(tensor));
    assertEquals(tensor.length(), DateTimeInterval.values().length);
  }

  @Test
  void testHalfMonth() {
    assertEquals(DateTimeInterval.HALF_MONTH.plus(LocalDateTime.of(2022, 3, 4, 2, 3)), LocalDateTime.of(2022, 3, 16, 2, 3));
    assertEquals(DateTimeInterval.HALF_MONTH.plus(LocalDateTime.of(2022, 2, 28, 2, 3)), LocalDateTime.of(2022, 3, 1, 2, 3));
  }

  @Test
  void monthStepHint() {
    assertEquals(DateTimeInterval._1_MO.durationHint(), Quantity.of(2678400, "s"));
  }

  @ParameterizedTest
  @EnumSource
  void invariant(DateTimeInterval dateTimeInterval) {
    LocalDateTime localDateTime = LocalDateTime.of(1900, 1, 1, 0, 0); // <- was a Monday
    assertEquals(localDateTime, dateTimeInterval.floor(localDateTime));
  }

  @ParameterizedTest
  @EnumSource
  void differentMinus(DateTimeInterval dateTimeInterval) {
    LocalDateTime localDateTime = LocalDateTime.of(1900, 1, 1, 0, 0); // <- was a Monday
    assertNotEquals(localDateTime, dateTimeInterval.floorOrMinus(localDateTime));
  }

  @Test
  void findAboveEquals() {
    assertEquals(DateTimeInterval._05_S, DateTimeInterval.findAboveEquals(Quantity.of(3, "s")));
    assertEquals(DateTimeInterval._15_S, DateTimeInterval.findAboveEquals(Quantity.of(15, "s")));
    assertEquals(DateTimeInterval._06_H, DateTimeInterval.findAboveEquals(Quantity.of(5, "h")));
  }
}
