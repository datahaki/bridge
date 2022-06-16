// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ch.alpine.tensor.ext.HomeDirectory;

class FileBlockTest {
  @Test
  void testSimple() {
    assertFalse(FileBlock.of(HomeDirectory.file(), "abc", false));
    assertTrue(FileBlock.of(HomeDirectory.file(), "abc", false));
    assertTrue(FileBlock.of(HomeDirectory.file(), "abc", false));
  }

  @ParameterizedTest
  @ValueSource(strings = { "sdfghj_123", "sdsf-434", "aSd.23.txt", "-" })
  void testValidName(String value) {
    assertTrue(FileBlock.validFilename(value));
  }

  @ParameterizedTest
  @ValueSource(strings = { " a", "  ", "!", "+", "  **  * ", "asd wer", ". ", " " })
  void testInvalidName(String value) {
    assertFalse(FileBlock.validFilename(value));
  }
}
