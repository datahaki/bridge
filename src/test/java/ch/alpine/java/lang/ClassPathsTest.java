// code by jph
package ch.alpine.java.lang;

import junit.framework.TestCase;

public class ClassPathsTest extends TestCase {
  public void testSimple() {
    String expected = String.join(System.getProperty("path.separator"), "b", "asd");
    assertEquals(expected, ClassPaths.join("b", null, "asd"));
  }

  public void testResource() {
    assertNotNull(ClassPaths.getResource());
  }
}
