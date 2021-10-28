// code by jph
package ch.alpine.java.fig;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.io.ResourceData;
import ch.alpine.tensor.sca.Clips;
import junit.framework.TestCase;

public class VisualImageTest extends TestCase {
  public void testSimple() {
    VisualImage visualImage = new VisualImage(ImageFormat.of(Array.zeros(10, 20, 4)), Clips.unit(), Clips.unit());
    visualImage.getAxisX();
  }

  public void testSparse() {
    VisualImage visualImage = new VisualImage(ImageFormat.of(Array.sparse(10, 20, 4)), Clips.unit(), Clips.unit());
    visualImage.getPlotLabel();
  }

  public void testImage() {
    BufferedImage bufferedImage = ResourceData.bufferedImage("/io/image/album_au_gray.jpg");
    VisualImage visualImage = new VisualImage(bufferedImage);
    assertEquals(visualImage.getAxisX().getClip().orElseThrow(), Clips.positive(bufferedImage.getWidth()));
    assertEquals(visualImage.getAxisY().getClip().orElseThrow(), Clips.positive(bufferedImage.getHeight()));
    JFreeChart jFreeChart = ArrayPlot.of(visualImage);
    jFreeChart.draw(bufferedImage.createGraphics(), new Rectangle2D.Double(0, 0, 200, 200));
  }
}
