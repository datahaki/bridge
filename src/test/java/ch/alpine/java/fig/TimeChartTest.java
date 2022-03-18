// code by jph
package ch.alpine.java.fig;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.DeleteDirectory;
import ch.alpine.tensor.ext.HomeDirectory;

public class TimeChartTest {
  @Test
  public void testAll() throws IOException {
    File folder = HomeDirectory.Pictures(getClass().getSimpleName());
    assertFalse(folder.exists());
    folder.mkdirs();
    TestHelper.cascade(new File(folder, "1"), true);
    TestHelper.cascade(new File(folder, "0"), false);
    DeleteDirectory.of(folder, 2, 20);
    assertFalse(folder.exists());
  }
}
