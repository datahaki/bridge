// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class HtmlUtf8Test {
  @TempDir
  Path tempDir;

  @Test
  void testSimple() throws IOException {
    Path file = tempDir.resolve("page.html");
    assertFalse(Files.exists(file));
    try (HtmlUtf8 htmlUtf8 = HtmlUtf8.page(file)) {
      htmlUtf8.appendln("some");
    }
    assertTrue(Files.exists(file));
  }

  @Test
  void testIndex() {
    Path file = tempDir.resolve("index.html");
    assertFalse(Files.exists(file));
    HtmlUtf8.index(file, "title", "cols", "fl", "vl", "fr", "vr");
    assertTrue(Files.exists(file));
  }
}
