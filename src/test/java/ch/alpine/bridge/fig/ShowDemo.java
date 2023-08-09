// code by jph
package ch.alpine.bridge.fig;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

@ReflectionMarker
public class ShowDemo implements Runnable {
  public Integer width = 400;
  public Integer height = 200;
  @FieldClip(min = "1", max = "5")
  public Integer mag = 2;
  // ---
  private final JFrame jFrame = new JFrame();
  private final JComponent jComponent = new JComponent() {
    @Override
    protected void paintComponent(Graphics graphics) {
      int ofs = 0;
      for (BufferedImage bufferedImage : list) {
        graphics.drawImage(bufferedImage, 0, ofs, width * mag, height * mag, null);
        ofs += height * mag;
      }
    }
  };
  private final JScrollPane jScrollPane = new JScrollPane(jComponent, //
      ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, //
      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
  private List<BufferedImage> list = new ArrayList<>();

  private List<BufferedImage> recomp() {
    List<BufferedImage> list = new ArrayList<>();
    for (ShowDemos showDemos : ShowDemos.values()) {
      try {
        Rectangle rectangle = Show.defaultInsets(new Dimension(width, height), 12);
        if (showDemos.extra)
          rectangle.width -= 100;
        Show show = showDemos.create();
        Objects.requireNonNull(show);
        BufferedImage bufferedImage = show.image(new Dimension(width, height), rectangle);
        Graphics2D graphics = bufferedImage.createGraphics();
        // java.awt.Font[family=Dialog,name=Dialog,style=plain,size=12]
        graphics.setColor(Color.PINK);
        graphics.drawRect(0, 0, width - 1, height - 1);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.setFont(new Font(Font.DIALOG, Font.PLAIN, 9));
        RenderQuality.setQuality(graphics);
        graphics.drawString(showDemos.name(), 0, 10);
        graphics.dispose();
        list.add(bufferedImage);
      } catch (Exception exception) {
        System.err.println(showDemos);
        exception.printStackTrace();
      }
    }
    Collections.reverse(list);
    return list;
  }

  private ShowDemo() {
    JScrollBar jScrollBar = jScrollPane.getVerticalScrollBar();
    jScrollBar.setPreferredSize(new Dimension(30, 30));
    run();
    JPanel jPanel = new JPanel(new BorderLayout());
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
      FieldsEditor fieldsEditor = ToolbarFieldsEditor.addToComponent(this, jToolBar);
      fieldsEditor.addUniversalListener(this);
      jPanel.add(BorderLayout.NORTH, jToolBar);
    }
    jPanel.add(BorderLayout.CENTER, jScrollPane);
    jFrame.setContentPane(jPanel);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setBounds(100, 100, 1000, 900);
  }

  @Override
  public void run() {
    list = recomp();
    int piy = ShowDemos.values().length * height * mag;
    jComponent.setPreferredSize(new Dimension(width, piy));
    jComponent.repaint();
    JViewport viewport = jScrollPane.getViewport();
    viewport.setViewPosition(new Point(0, piy - 1));
    viewport.setViewPosition(new Point(0, 0));
  }

  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    ShowDemo showDemo = new ShowDemo();
    showDemo.jFrame.setVisible(true);
  }
}
