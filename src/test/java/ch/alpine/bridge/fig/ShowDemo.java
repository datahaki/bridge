// code by jph
package ch.alpine.bridge.fig;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

@ReflectionMarker
public class ShowDemo {
  final int width = 400;
  final int height = 200;
  @FieldClip(min = "1", max = "5")
  public Integer mag = 2;
  // ---
  private final JFrame jFrame = new JFrame();
  private final List<BufferedImage> list = new ArrayList<>();
  private final JComponent jComponent = new JComponent() {
    @Override
    protected void paintComponent(Graphics graphics) {
      {
        Dimension dimension = jComponent.getSize();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, dimension.width, dimension.height);
      }
      int ofs = 0;
      for (BufferedImage bufferedImage : list) {
        Graphics graphics2 = bufferedImage.getGraphics();
        graphics2.setColor(Color.PINK);
        graphics2.drawRect(0, 0, width - 1, height - 1);
        graphics.drawImage(bufferedImage, 0, ofs, width * mag, height * mag, null);
        ofs += height * mag;
      }
    }
  };

  private ShowDemo() {
    // = show.image();
    for (ShowDemos showDemos : ShowDemos.values()) {
      try {
        list.add(showDemos.create().image(new Dimension(width, height)));
      } catch (Exception exception) {
        System.err.println(showDemos);
        exception.printStackTrace();
      }
    }
    Collections.reverse(list);
    jComponent.setPreferredSize(new Dimension(width, ShowDemos.values().length * height * mag));
    JScrollPane jScrollPane = new JScrollPane(jComponent, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    JScrollBar jScrollBar = jScrollPane.getVerticalScrollBar();
    jScrollBar.setPreferredSize(new Dimension(30, 30));
    JPanel jPanel = new JPanel(new BorderLayout());
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
      FieldsEditor fieldsEditor = ToolbarFieldsEditor.add(this, jToolBar);
      fieldsEditor.addUniversalListener(() -> {
        jComponent.setPreferredSize(new Dimension(width, ShowDemos.values().length * height * mag));
        jComponent.repaint();
      });
      jPanel.add(BorderLayout.NORTH, jToolBar);
    }
    jPanel.add(BorderLayout.CENTER, jScrollPane);
    jFrame.setContentPane(jPanel);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setBounds(100, 100, 1000, 900);
  }

  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    ShowDemo showDemo = new ShowDemo();
    showDemo.jFrame.setVisible(true);
  }
}
