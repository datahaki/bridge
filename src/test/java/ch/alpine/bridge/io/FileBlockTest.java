// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.HomeDirectory;

class FileBlockTest {
  @Test
  void testSimple() {
    assertFalse(FileBlock.of(HomeDirectory.file(), getClass(), false));
    assertTrue(FileBlock.of(HomeDirectory.file(), getClass(), false));
    assertTrue(FileBlock.of(HomeDirectory.file(), getClass(), false));
  }
}
