// code by jph
package ch.alpine.bridge.demo.fig;

import java.awt.Dimension;
import java.awt.Window;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowGridComponent;
import ch.alpine.bridge.pro.WindowProvider;

class ShowWindowDemo implements WindowProvider {
  @Override
  public Window getWindow() {
    JFrame jFrame = new JFrame();
    List<Show> list = Arrays.stream(Showcases.values()).map(Showcases::getShow).toList();
    JScrollPane jScrollPane = new JScrollPane(ShowGridComponent.column(list, new Dimension(400, 400)));
    jFrame.setContentPane(jScrollPane);
    return jFrame;
  }

  static void main() {
    new ShowWindowDemo().runStandalone();
  }
}
