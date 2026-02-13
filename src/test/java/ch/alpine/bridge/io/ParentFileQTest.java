// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.HomeDirectory;

class ParentFileQTest {
  @Test
  void test() {
    File base = HomeDirectory.Documents.resolve("").toFile();
    assertTrue(ParentFileQ.test(base, HomeDirectory.Documents.resolve("more").toFile()));
    assertFalse(ParentFileQ.test(HomeDirectory.Documents.resolve("more").toFile(), base));
    assertTrue(ParentFileQ.test(base, HomeDirectory.Documents.resolve("more", "test.txt").toFile()));
    assertFalse(ParentFileQ.test(HomeDirectory.Desktop.resolve().toFile(), HomeDirectory.Documents.resolve().toFile()));
  }
}
