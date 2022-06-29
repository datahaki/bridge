// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.ext.HomeDirectory;

class PropertiesExtTest {
  @Test
  void testStoreLoadISO8859_1(@TempDir File folder) throws IOException {
    final String string = "special\u00e3tab\tnewline\nbackslash\\termination";
    File file = new File(folder, "sample.properties");
    Charset charset = StandardCharsets.ISO_8859_1;
    {
      Properties properties = new Properties();
      properties.setProperty("key", string);
      try (FileWriter fileWriter = new FileWriter(file, charset)) {
        properties.store(fileWriter, null);
      }
    }
    {
      Properties properties = new Properties();
      try (FileReader fileWriter = new FileReader(file, charset)) {
        properties.load(fileWriter);
      }
      String property = properties.getProperty("key");
      assertEquals(property, string);
    }
  }

  @Test
  void testStoreLoadUTF8(@TempDir File folder) throws IOException {
    final String string = "special\u00e3tab\tnewline\nbackslash\\termination\u3000&#blub";
    File file = new File(folder, "sample.properties");
    file = HomeDirectory.file("some.properties");
    Charset charset = StandardCharsets.UTF_8;
    {
      Properties properties = new Properties();
      properties.setProperty("key", string);
      try (FileWriter fileWriter = new FileWriter(file, charset)) {
        properties.store(fileWriter, null);
      }
    }
    {
      Properties properties = new Properties();
      try (FileReader fileWriter = new FileReader(file, charset)) {
        properties.load(fileWriter);
      }
      String property = properties.getProperty("key");
      assertEquals(property, string);
    }
  }
}
