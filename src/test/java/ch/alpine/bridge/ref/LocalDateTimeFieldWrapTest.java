// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class LocalDateTimeFieldWrapTest {
  @Test
  void test(@TempDir File folder) throws IOException {
    TimeParam timeParam = new TimeParam();
    timeParam.dateTime = LocalDateTime.of(2020, 1, 1, 0, 0);
    File file = new File(folder, "some.properties");
    ObjectProperties.save(timeParam, file);
    timeParam = new TimeParam();
    ObjectProperties.load(timeParam, file);
    assertEquals(timeParam.dateTime.toString(), "2020-01-01T00:00");
  }
}
