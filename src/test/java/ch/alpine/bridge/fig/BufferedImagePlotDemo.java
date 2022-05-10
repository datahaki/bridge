// code by jph
package ch.alpine.bridge.fig;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartPanel;

public class BufferedImagePlotDemo {
  private final JFrame jFrame = new JFrame();

  public BufferedImagePlotDemo() {
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setContentPane(new ChartPanel(SpectrogramDemo.create(0.32, 1.6)));
    jFrame.setBounds(100, 100, 600, 600);
    jFrame.setVisible(true);
  }

  @SuppressWarnings("unused")
  public static void main(String[] args) {
    new BufferedImagePlotDemo();
  }
}
