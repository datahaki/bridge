// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.DeleteDirectory;
import ch.alpine.tensor.ext.HomeDirectory;

class TimeChartTest {
  @Test
  void testAll() throws IOException {
    File folder = HomeDirectory.Pictures(getClass().getSimpleName());
    assertFalse(folder.exists());
    folder.mkdirs();
    CascadeHelper.cascade(new File(folder, "1"), true);
    CascadeHelper.cascade(new File(folder, "0"), false);
    DeleteDirectory.of(folder, 2, 20);
    assertFalse(folder.exists());
  }
}
