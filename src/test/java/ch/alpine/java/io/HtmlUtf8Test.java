// code by jph
package ch.alpine.java.io;

import java.io.File;

import ch.alpine.tensor.ext.HomeDirectory;
import junit.framework.TestCase;

public class HtmlUtf8Test extends TestCase {
  public void testSimple() {
    File file = HomeDirectory.file(HtmlUtf8Test.class.getSimpleName() + ".html");
    assertFalse(file.exists());
    HtmlUtf8 htmlUtf8 = HtmlUtf8.page(file);
    htmlUtf8.appendln("some");
    htmlUtf8.close();
    assertTrue(file.exists());
    file.delete();
  }
}
