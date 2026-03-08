// code by jph
package ch.alpine.bridge.demo.fig;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.pro.ManipulateProvider;
import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.img.ImageResize;

@ReflectionMarker
public class ShowDemo implements ManipulateProvider {
  public Integer width = 300;
  public Integer height = 200;
  @FieldClip(min = "1", max = "5")
  public Integer mag = 2;
  public Boolean noInset = false;
  private final int SPACING = 10;
  // ---
  private final JComponent jComponent = new JComponent() {
    @Override
    protected void paintComponent(Graphics graphics) {
      int ofs = 0;
      for (BufferedImage bufferedImage : list) {
        BufferedImage dst = ImageResize.DEGREE_0.of(bufferedImage, width * mag, height * mag);
        graphics.drawImage(dst, 0, ofs, null);
        ofs += height * mag;
        ofs += SPACING;
      }
    }
  };
  private final JScrollPane jScrollPane = new JScrollPane(jComponent, //
      ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, //
      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
  private List<BufferedImage> list = new ArrayList<>();

  public ShowDemo() {
    jScrollPane.getVerticalScrollBar().setUnitIncrement(50);
    JScrollBar jScrollBar = jScrollPane.getVerticalScrollBar();
    jScrollBar.setPreferredSize(new Dimension(30, 30));
  }

  private List<BufferedImage> recomp() {
    List<BufferedImage> list = new ArrayList<>();
    for (Showcases showcases : Showcases.values())
      try {
        Dimension dimension = new Dimension(width, height);
        Rectangle rectangle = Show.defaultInsets(dimension, 12);
        if (showcases.extra)
          rectangle.width -= 100;
        if (noInset)
          rectangle = new Rectangle(new Point(), dimension);
        Show show = showcases.getShow();
        Objects.requireNonNull(show);
        BufferedImage bufferedImage = show.image(dimension, rectangle);
        Graphics2D graphics = bufferedImage.createGraphics();
        // java.awt.Font[family=Dialog,name=Dialog,style=plain,size=12]
        graphics.setColor(new Color(255, 175, 175, 64));
        graphics.drawRect(0, 0, width - 1, height - 1);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.setFont(new Font(Font.DIALOG, Font.PLAIN, 9));
        RenderQuality.setQuality(graphics);
        graphics.drawString(showcases.name(), 0, 10);
        graphics.dispose();
        list.add(bufferedImage);
      } catch (Exception exception) {
        System.err.println(showcases);
        exception.printStackTrace();
      }
    return list;
  }

  @Override
  public Container getContainer() {
    list = recomp();
    int piy = Showcases.values().length * (height * mag + SPACING);
    jComponent.setPreferredSize(new Dimension(width, piy));
    JViewport jViewport = jScrollPane.getViewport();
    jViewport.setViewPosition(new Point(0, 0));
    // chatgpt:
    // "Whenever the contents of a JScrollPane change size
    // then call revalidate() on the viewport view."
    jViewport.revalidate();
    return jScrollPane;
  }

  static void main() {
    new ShowDemo().runStandalone();
  }
}
