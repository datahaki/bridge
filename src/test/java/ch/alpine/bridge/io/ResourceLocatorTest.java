// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ResourceLocatorTest {
  @TempDir
  Path tempDir;

  @Test
  void test() {
    ResourceLocator resourceLocator = new ResourceLocator(tempDir);
    assertEquals(resourceLocator.resolve(""), tempDir);
    Path file = resourceLocator.properties(getClass());
    assertFalse(Files.isRegularFile(file));
    resourceLocator.tryLoad(new ResourceLocatorTest());
    resourceLocator.trySave(new ResourceLocatorTest());
    ResourceLocator rl2 = resourceLocator.sub("here");
    Path path = tempDir.resolve("here");
    assertTrue(Files.isDirectory(path));
    assertEquals(rl2.resolve(""), path);
  }

  @Test
  void testNull() {
    assertThrows(Exception.class, () -> new ResourceLocator(null));
  }
}
