// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.plt.ListLinePlot;
import ch.alpine.tensor.io.Primitives;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

class ZipDirectoryTest {
  @TempDir
  Path tempDir;

  @Test
  void testSimple() throws Exception {
    Path folder = tempDir.resolve("folder");
    assertFalse(Files.exists(folder));
    Files.createDirectories(folder);
    {
      Show show = new Show();
      show.add(ListLinePlot.of(RandomVariate.of(UniformDistribution.of(2, 3), 10, 2)));
      show.export(folder.resolve("image.png"), new Dimension(300, 200));
    }
    Path zipFile = tempDir.resolve("file.zip");
    assertFalse(Files.isRegularFile(zipFile));
    ZipDirectory.of(folder, zipFile);
    DeleteDirectory.of(folder, 1, 10);
    Files.delete(zipFile);
  }

  @Test
  void testBinary() throws IOException {
    Path file = tempDir.resolve("file");
    assertFalse(Files.exists(file));
    {
      byte[] array = Primitives.toByteArray(RandomVariate.of(UniformDistribution.unit(), 23948));
      Files.write(file, array);
      byte[] read = Files.readAllBytes(file);
      assertArrayEquals(array, read);
    }
  }
}
