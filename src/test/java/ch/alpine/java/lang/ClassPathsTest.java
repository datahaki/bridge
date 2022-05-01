// code by jph
package ch.alpine.java.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ClassPathsTest {
  @Test
  public void testSimple() {
    String expected = String.join(System.getProperty("path.separator"), "b", "asd");
    assertEquals(expected, ClassPaths.join("b", null, "asd"));
  }

  @Test
  public void testResource() {
    assertNotNull(ClassPaths.getResource());
  }
}
