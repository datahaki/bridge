// code by jph
package ch.alpine.bridge.usr;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.swing.LookAndFeels;

public class ShowDemo {
  final int width = 400;
  final int height = 200;
  final int mag = 2;
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
        try {
          Insets insets = Show.defaultInsets();
          show.render(graphics2, new Rectangle( //
              insets.left, insets.top, //
              width - insets.left - insets.right, //
              height - insets.top - insets.bottom));
        } catch (Exception exception) {
          graphics2.setColor(Color.RED);
          graphics2.drawString("" + exception.getMessage(), 0, 30);
          exception.printStackTrace();
        }
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
    JScrollBar jScrollBar = jScrollPane.getVerticalScrollBar();
    jScrollBar.setPreferredSize(new Dimension(30, 30));
    jFrame.setContentPane(jScrollPane);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setBounds(100, 100, 1000, 900);
  }

  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    ShowDemo showDemo = new ShowDemo();
    showDemo.jFrame.setVisible(true);
  }
}
