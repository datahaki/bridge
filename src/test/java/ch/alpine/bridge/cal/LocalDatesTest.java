// code by all
package ch.alpine.bridge.cal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

class LocalDatesTest {
  @Test
  void test() {
    assertEquals(LocalDates._1_WK.floor(LocalDate.of(2022, Month.SEPTEMBER, 29)), LocalDate.of(2022, 9, 26));
    assertEquals(LocalDates._3_MO.floor(LocalDate.of(2022, Month.SEPTEMBER, 29)), LocalDate.of(2022, 7, 1));
    assertEquals(LocalDates.HALF_MONTH.floor(LocalDate.of(2022, Month.SEPTEMBER, 29)), LocalDate.of(2022, Month.SEPTEMBER, 16));
    assertEquals(LocalDates.HALF_MONTH.floor(LocalDate.of(2022, Month.SEPTEMBER, 13)), LocalDate.of(2022, Month.SEPTEMBER, 1));
  }
}
