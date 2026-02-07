// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class PropertiesExtTest {
  @TempDir
  Path tempDir;

  @Test
  void testStoreLoadISO8859_1() throws IOException {
    final String string = "special\u00e3tab\tnewline\nbackslash\\termination";
    Path file = tempDir.resolve("sample1.properties");
    Charset charset = StandardCharsets.ISO_8859_1;
    {
      Properties properties = new Properties();
      properties.setProperty("key", string);
      try (BufferedWriter fileWriter = Files.newBufferedWriter(file, charset)) {
        properties.store(fileWriter, null);
      }
    }
    {
      Properties properties = new Properties();
      try (BufferedReader fileWriter = Files.newBufferedReader(file, charset)) {
        properties.load(fileWriter);
      }
      String property = properties.getProperty("key");
      assertEquals(property, string);
    }
  }

  @Test
  void testStoreLoadUTF8() throws IOException {
    final String string = "special\u00e3tab\tnewline\nbackslash\\termination\u3000&#blub";
    Path file = tempDir.resolve("sample2.properties");
    Charset charset = StandardCharsets.UTF_8;
    {
      Properties properties = new Properties();
      properties.setProperty("key", string);
      try (BufferedWriter fileWriter = Files.newBufferedWriter(file, charset)) {
        properties.store(fileWriter, null);
      }
    }
    {
      Properties properties = new Properties();
      try (BufferedReader fileWriter = Files.newBufferedReader(file, charset)) {
        properties.load(fileWriter);
      }
      String property = properties.getProperty("key");
      assertEquals(property, string);
    }
  }
}
