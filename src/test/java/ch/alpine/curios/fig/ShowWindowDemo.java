// code by jph
package ch.alpine.curios.fig;

import java.awt.Dimension;
import java.awt.Window;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowGridComponent;
import ch.alpine.bridge.pro.WindowProvider;

public class ShowWindowDemo implements WindowProvider {
  @Override
  public Window getWindow() {
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    List<Show> list = Stream.of(Showcases.values()).map(Showcases::getShow).toList();
    JScrollPane jScrollPane = new JScrollPane(ShowGridComponent.column(list, new Dimension(400, 400)));
    jFrame.setContentPane(jScrollPane);
    return jFrame;
  }

  static void main() {
    new ShowWindowDemo().runStandalone();
  }
}
