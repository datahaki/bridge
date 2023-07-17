// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ResourceLocatorTest {
  @Test
  void test(@TempDir File folder) {
    ResourceLocator resourceLocator = new ResourceLocator(folder);
    assertEquals(resourceLocator.file(""), folder);
    File file = resourceLocator.properties(getClass());
    assertFalse(file.exists());
    resourceLocator.tryLoad(new ResourceLocatorTest());
    resourceLocator.trySave(new ResourceLocatorTest());
    ResourceLocator rl2 = resourceLocator.sub("here");
    assertTrue(new File(folder, "here").isDirectory());
    assertEquals(rl2.file(""), new File(folder, "here"));
  }

  @Test
  void testNull() {
    assertThrows(Exception.class, () -> new ResourceLocator(null));
  }
}
