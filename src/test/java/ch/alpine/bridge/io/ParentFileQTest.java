// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.HomeDirectory;

class ParentFileQTest {
  @Test
  void test() {
    assertTrue(ParentFileQ.test(HomeDirectory.file().toFile(), HomeDirectory.Documents().toFile()));
    assertTrue(ParentFileQ.test(HomeDirectory.file().toFile(), HomeDirectory.Documents("test.txt").toFile()));
    assertFalse(ParentFileQ.test(HomeDirectory.Desktop().toFile(), HomeDirectory.Documents().toFile()));
  }
}
