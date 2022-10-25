// code by jph
package ch.alpine.bridge.fig;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.alpine.bridge.swing.LookAndFeels;

public class ShowComponentDemo {
  private final JFrame jFrame = new JFrame();

  public ShowComponentDemo() {
    ShowComponent chartComponent = new ShowComponent(ShowDemos.DEMO1.create());
    jFrame.setContentPane(chartComponent);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setBounds(100, 100, 1000, 900);
  }

  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    ShowComponentDemo showDemo = new ShowComponentDemo();
    showDemo.jFrame.setVisible(true);
  }
}
