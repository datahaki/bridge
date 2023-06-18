// code by jph
package ch.alpine.bridge.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class LocalDateParamTest {
  @Test
  void test() {
    LocalDate localDate = LocalDate.now();
    LocalDateParam localDateParam = new LocalDateParam(localDate);
    assertEquals(localDateParam.toLocalDate(), localDate);
    assertEquals(localDateParam.toString(), localDate.toString());
    assertThrows(Exception.class, () -> localDateParam.set(null));
    localDateParam.years();
    localDateParam.days();
  }
}
