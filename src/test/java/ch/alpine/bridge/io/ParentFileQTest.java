package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.HomeDirectory;

class ParentFileQTest {
  @Test
  void test() {
    assertTrue(ParentFileQ.test(HomeDirectory.file(), HomeDirectory.Documents()));
    assertTrue(ParentFileQ.test(HomeDirectory.file(), HomeDirectory.Documents("test.txt")));
    assertFalse(ParentFileQ.test(HomeDirectory.Desktop(), HomeDirectory.Documents()));
  }
}
