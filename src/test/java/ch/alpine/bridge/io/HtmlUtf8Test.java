// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class HtmlUtf8Test {
  @Test
  void testSimple(@TempDir File tempDir) {
    File file = new File(tempDir, "file.html");
    assertFalse(file.exists());
    try (HtmlUtf8 htmlUtf8 = HtmlUtf8.page(file)) {
      htmlUtf8.appendln("some");
    }
    assertTrue(file.exists());
  }

  @Test
  void testIndex(@TempDir File tempDir) {
    File file = new File(tempDir, "file.html");
    assertFalse(file.exists());
    HtmlUtf8.index(file, "title", "cols", "fl", "vl", "fr", "vr");
    assertTrue(file.exists());
  }
}
