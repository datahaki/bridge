// code by jph
package ch.alpine.java.io;

import ch.alpine.tensor.ext.HomeDirectory;
import junit.framework.TestCase;

public class FileBlockTest extends TestCase {
  public void testSimple() {
    assertFalse(FileBlock.of(HomeDirectory.file(), getClass(), false));
    assertTrue(FileBlock.of(HomeDirectory.file(), getClass(), false));
    assertTrue(FileBlock.of(HomeDirectory.file(), getClass(), false));
  }
}
