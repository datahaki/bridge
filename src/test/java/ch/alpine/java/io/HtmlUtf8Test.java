// code by jph
package ch.alpine.java.io;

import java.io.File;

import ch.alpine.tensor.ext.HomeDirectory;
import junit.framework.TestCase;

public class HtmlUtf8Test extends TestCase {
  public void testSimple() {
    File file = HomeDirectory.file(HtmlUtf8Test.class.getSimpleName() + "Page.html");
    assertFalse(file.exists());
    try (HtmlUtf8 htmlUtf8 = HtmlUtf8.page(file)) {
      htmlUtf8.appendln("some");
    }
    assertTrue(file.exists());
    file.delete();
  }

  public void testIndex() {
    File file = HomeDirectory.file(HtmlUtf8Test.class.getSimpleName() + "Index.html");
    assertFalse(file.exists());
    HtmlUtf8.index(file, "title", "cols", "fl", "vl", "fr", "vr");
    assertTrue(file.exists());
    file.delete();
  }
}
