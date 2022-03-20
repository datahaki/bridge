// code by jph
package ch.alpine.java.io;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.java.fig.ListPlot;
import ch.alpine.java.fig.VisualSet;
import ch.alpine.tensor.ext.DeleteDirectory;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

public class ZipDirectoryTest {
  @Test
  public void testSimple(@TempDir File tempDir) throws FileNotFoundException, IOException {
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
}
