// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.ext.DeleteDirectory;
import ch.alpine.tensor.io.Primitives;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

class ZipDirectoryTest {
  @Test
  void testSimple(@TempDir File tempDir) throws IOException {
    File folder = new File(tempDir, "folder");
    assertFalse(folder.exists());
    folder.mkdirs();
    {
      VisualSet visualSet = new VisualSet();
      visualSet.add(RandomVariate.of(UniformDistribution.of(2, 3), 10, 2));
      JFreeChart jFreeChart = ListPlot.of(visualSet);
      ChartUtils.saveChartAsJPEG(new File(folder, "image.jpg"), jFreeChart, 300, 200);
    }
    File zipFile = new File(tempDir, "file.zip");
    assertFalse(zipFile.exists());
    ZipDirectory.of(folder, zipFile);
    DeleteDirectory.of(folder, 1, 10);
    zipFile.delete();
  }

  @Test
  void testBinary(@TempDir File tempDir) throws IOException {
    File file = new File(tempDir, "file");
    assertFalse(file.exists());
    {
      byte[] array = Primitives.toByteArray(RandomVariate.of(UniformDistribution.unit(), 23948));
      Files.write(file.toPath(), array);
      byte[] read = Files.readAllBytes(file.toPath());
      assertArrayEquals(array, read);
    }
  }
}
