// code by jph
package ch.alpine.bridge.usr;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jfree.chart.swing.ChartPanel;

public class BufferedImagePlotDemo {
  public BufferedImagePlotDemo() {
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setContentPane(new ChartPanel(SpectrogramDemo.create()));
    jFrame.setBounds(100, 100, 600, 600);
    jFrame.setVisible(true);
  }

  @SuppressWarnings("unused")
  public static void main(String[] args) {
    new BufferedImagePlotDemo();
  }
}
