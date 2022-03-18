// code by jph
package ch.alpine.java.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.HomeDirectory;

public class HtmlUtf8Test {
  @Test
  public void testSimple() {
    File file = HomeDirectory.file(HtmlUtf8Test.class.getSimpleName() + "Page.html");
    assertFalse(file.exists());
    try (HtmlUtf8 htmlUtf8 = HtmlUtf8.page(file)) {
      htmlUtf8.appendln("some");
    }
    assertTrue(file.exists());
    file.delete();
  }

  @Test
  public void testIndex() {
    File file = HomeDirectory.file(HtmlUtf8Test.class.getSimpleName() + "Index.html");
    assertFalse(file.exists());
    HtmlUtf8.index(file, "title", "cols", "fl", "vl", "fr", "vr");
    assertTrue(file.exists());
    file.delete();
  }
}
