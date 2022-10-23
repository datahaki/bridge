// code by jph
package ch.alpine.bridge.usr;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import ch.alpine.bridge.fig.Show;

public class ShowDemo {
  final int _WIDTH = 301;
  final int _HEIGHT = 131;
  final int width = _WIDTH + 90;
  final int height = _HEIGHT + 30;
  final int mag = 1;
  // ---
  private final JFrame jFrame = new JFrame();
  private final List<Show> list = new ArrayList<>();
  private final JComponent jComponent = new JComponent() {
    @Override
    protected void paintComponent(Graphics graphics) {
      {
        Dimension dimension = jComponent.getSize();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, dimension.width, dimension.height);
      }
      int ofs = 0;
      for (Show show : list) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics2 = bufferedImage.getGraphics();
        graphics2.setColor(Color.PINK);
        graphics2.drawRect(0, 0, width - 1, height - 1);
        show.render(new Rectangle(70, 5, _WIDTH, _HEIGHT), graphics2);
        graphics.drawImage(bufferedImage, 0, ofs, width * mag, height * mag, null);
        ofs += height * mag;
      }
    }
  };

  public ShowDemo() {
    for (ShowDemos showDemos : ShowDemos.values())
      list.add(showDemos.create());
    Collections.reverse(list);
    jComponent.setPreferredSize(new Dimension(width, ShowDemos.values().length * height * mag));
    JScrollPane jScrollPane = new JScrollPane(jComponent, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    jFrame.setContentPane(jScrollPane);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setBounds(100, 100, 1400, 900);
    jFrame.setVisible(true);
  }

  public static void main(String[] args) {
    ShowDemo showDemo = new ShowDemo();
  }
}
