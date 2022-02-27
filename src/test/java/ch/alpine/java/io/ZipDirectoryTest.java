// code by jph
package ch.alpine.java.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.java.fig.ListPlot;
import ch.alpine.java.fig.VisualSet;
import ch.alpine.tensor.ext.DeleteDirectory;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import junit.framework.TestCase;

public class ZipDirectoryTest extends TestCase {
  public void testSimple() throws FileNotFoundException, IOException {
    File folder = new File(ZipDirectoryTest.class.getSimpleName());
    assertFalse(folder.exists());
    folder.mkdirs();
    {
      VisualSet visualSet = new VisualSet();
      visualSet.add(RandomVariate.of(UniformDistribution.of(2, 3), 10, 2));
      JFreeChart jFreeChart = ListPlot.of(visualSet);
      ChartUtils.saveChartAsJPEG(new File(folder, "image.jpg"), jFreeChart, 300, 200);
    }
    File zipFile = HomeDirectory.file(ZipDirectoryTest.class.getSimpleName() + ".zip");
    assertFalse(zipFile.exists());
    ZipDirectory.of(folder, zipFile);
    DeleteDirectory.of(folder, 1, 10);
    zipFile.delete();
  }
}
