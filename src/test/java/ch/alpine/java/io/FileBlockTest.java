// code by jph
package ch.alpine.java.io;

import ch.alpine.tensor.ext.HomeDirectory;
import junit.framework.TestCase;

public class FileBlockTest extends TestCase {
  public void testSimple() {
    assertFalse(new FileBlock(HomeDirectory.file(), getClass()).defaultMessage());
    assertTrue(new FileBlock(HomeDirectory.file(), getClass()).isActive());
    assertTrue(new FileBlock(HomeDirectory.file(), getClass()).isActive());
  }
}
