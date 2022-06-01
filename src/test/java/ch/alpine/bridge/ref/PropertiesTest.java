// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.io.Import;

class PropertiesTest {
  @Test
  void test(@TempDir File folder) throws IOException {
    File target = new File(folder, "some.properties");
    File file = new File("some\\folder\\file.txt");
    assertEquals(file.toString().length(), 20);
    {
      Properties properties = new Properties();
      properties.setProperty("file", file.toString());
      properties.setProperty("multiline", "abc\nnext");
      ExportExt.properties(target, properties);
    }
    Properties properties = Import.properties(target);
    assertEquals(properties.getProperty("file"), file.toString());
    assertEquals(properties.getProperty("multiline"), "abc\nnext");
  }
}
