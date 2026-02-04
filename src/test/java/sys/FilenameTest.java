// code by jph
package sys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.HomeDirectory;

class FilenameTest {
  @Test
  void testFile() {
    Filename filename = new Filename(HomeDirectory.file("some.properties"));
    assertEquals(filename.title(), "some");
    assertEquals(filename.extension(), "properties");
    assertEquals(filename.withExtension("txt"), HomeDirectory.file("some.txt"));
    assertThrows(Exception.class, () -> filename.withExtension(null));
  }

  @Test
  void testDirectory() {
    Filename filename = new Filename(HomeDirectory.Documents());
    assertEquals(filename.extension(), "");
  }

  @Test
  void testHiddenDirectory() {
    Filename filename = new Filename(HomeDirectory.Documents(".git"));
    assertEquals(filename.extension(), "");
    assertEquals(filename.title(), ".git");
  }
}
