// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.HomeDirectory;

class ParentFileQTest {
  @Test
  void test() {
    assertTrue(ParentFileQ.test(HomeDirectory.path().toFile(), HomeDirectory.Documents.resolve().toFile()));
    assertTrue(ParentFileQ.test(HomeDirectory.path().toFile(), HomeDirectory.Documents.resolve("test.txt").toFile()));
    assertFalse(ParentFileQ.test(HomeDirectory.Desktop.resolve().toFile(), HomeDirectory.Documents.resolve().toFile()));
  }
}
