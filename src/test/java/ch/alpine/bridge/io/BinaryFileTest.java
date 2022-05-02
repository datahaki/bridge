// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.io.Primitives;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

class BinaryFileTest {
  @Test
  public void testSimple(@TempDir File tempDir) throws FileNotFoundException, IOException {
    File file = new File(tempDir, "file");
    assertFalse(file.exists());
    {
      byte[] array = Primitives.toByteArray(RandomVariate.of(UniformDistribution.unit(), 23948));
      BinaryFile.write(file, array);
      byte[] read = BinaryFile.read(file);
      assertTrue(Arrays.equals(array, read));
    }
  }
}
