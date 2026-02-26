// code by jph
package ch.alpine.bridge.demo.fig;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.pro.WindowProvider;
import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;
import ch.alpine.tensor.img.ImageResize;

@ReflectionMarker
public class ShowDemo implements WindowProvider {
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
        BufferedImage dst = ImageResize.DEGREE_0.of(bufferedImage, width * mag, height * mag);
        graphics.drawImage(dst, 0, ofs, null);
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
    for (Showcases showcases : Showcases.values()) {
      try {
        Rectangle rectangle = Show.defaultInsets(new Dimension(width, height), 12);
        if (showcases.extra)
          rectangle.width -= 100;
        Show show = showcases.getShow();
        Objects.requireNonNull(show);
        BufferedImage bufferedImage = show.image(new Dimension(width, height), rectangle);
        Graphics2D graphics = bufferedImage.createGraphics();
        // java.awt.Font[family=Dialog,name=Dialog,style=plain,size=12]
        graphics.setColor(Color.PINK);
        graphics.drawRect(0, 0, width - 1, height - 1);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.setFont(new Font(Font.DIALOG, Font.PLAIN, 9));
        RenderQuality.setQuality(graphics);
        graphics.drawString(showcases.name(), 0, 10);
        graphics.dispose();
        list.addFirst(bufferedImage);
      } catch (Exception exception) {
        System.err.println(showcases);
        exception.printStackTrace();
      }
    }
    return list;
  }

  private ShowDemo() {
    JScrollBar jScrollBar = jScrollPane.getVerticalScrollBar();
    jScrollBar.setPreferredSize(new Dimension(30, 30));
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        list = recomp();
        int piy = Showcases.values().length * height * mag;
        jComponent.setPreferredSize(new Dimension(width, piy));
        jComponent.repaint();
        JViewport viewport = jScrollPane.getViewport();
        viewport.setViewPosition(new Point(0, piy - 1));
        viewport.setViewPosition(new Point(0, 0));
      }
    };
    runnable.run();
    JPanel jPanel = new JPanel(new BorderLayout());
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
      FieldsEditor fieldsEditor = ToolbarFieldsEditor.addToComponent(this, jToolBar);
      fieldsEditor.addUniversalListener(runnable);
      jPanel.add(BorderLayout.NORTH, jToolBar);
    }
    jPanel.add(BorderLayout.CENTER, jScrollPane);
    jFrame.setContentPane(jPanel);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }

  @Override
  public Window getWindow() {
    return jFrame;
  }

  static void main() {
    new ShowDemo().runStandalone();
  }
}
