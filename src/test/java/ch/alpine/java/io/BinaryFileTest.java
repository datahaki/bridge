// code by jph
package ch.alpine.java.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.io.Primitives;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import junit.framework.TestCase;

public class BinaryFileTest extends TestCase {
  public void testSimple() throws FileNotFoundException, IOException {
    File file = HomeDirectory.file(getClass().getSimpleName());
    assertFalse(file.exists());
    {
      byte[] array = Primitives.toByteArray(RandomVariate.of(UniformDistribution.unit(), 23948));
      BinaryFile.write(file, array);
      byte[] read = BinaryFile.read(file);
      assertTrue(Arrays.equals(array, read));
    }
    file.delete();
  }
}
