// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.ext.PackageTestAccess;

class FileBlockTest {
  @Test
  void testSimple() {
    Path path = HomeDirectory._local_share.mk_dirs(FileBlockTest.class.getName().split("\\."));
    assertFalse(FileBlock.of(path, false));
    assertTrue(FileBlock.of(path, false));
    assertTrue(FileBlock.of(path, false));
  }

  private static final Pattern PATTERN = Pattern.compile("^[\\w-.]{1,255}$");

  @PackageTestAccess
  static boolean validFilename(String string) {
    return PATTERN.matcher(string).matches();
  }

  @ParameterizedTest
  @ValueSource(strings = { "sdfghj_123", "sdsf-434", "aSd.23.txt", "-" })
  void testValidName(String value) {
    assertTrue(validFilename(value));
  }

  @ParameterizedTest
  @ValueSource(strings = { " a", "  ", "!", "+", "  **  * ", "asd wer", ". ", " " })
  void testInvalidName(String value) {
    assertFalse(validFilename(value));
  }
}
