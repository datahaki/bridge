// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.bridge.ref.util.ObjectProperties;

class LocalDateTimeFieldWrapTest {
  @Test
  void test(@TempDir File folder) throws IOException {
    TimeParam timeParam = new TimeParam();
    timeParam.dateTime = LocalDateTime.of(2020, 1, 1, 0, 0);
    timeParam.date = LocalDate.of(1923, 12, 31);
    timeParam.time = LocalTime.of(23, 59, 33);
    File file = new File(folder, "file.properties");
    ObjectProperties.save(timeParam, file);
    timeParam = new TimeParam();
    ObjectProperties.load(timeParam, file);
    assertEquals(timeParam.dateTime.toString(), "2020-01-01T00:00");
    assertEquals(timeParam.date.toString(), "1923-12-31");
    assertEquals(timeParam.time.toString(), "23:59:33");
  }
}
