package ch.alpine.bridge.usr;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.tri.Cos;

public class ShowDemo {
  private final JFrame jFrame = new JFrame();
  private final Show show = new Show(ColorDataLists._109.strict().deriveWithAlpha(192));
  private final JComponent jComponent = new JComponent() {
    @Override
    protected void paintComponent(Graphics graphics) {
      Dimension dimension = jComponent.getSize();
      graphics.setColor(Color.WHITE);
      graphics.fillRect(0, 0, dimension.width, dimension.height);
      show.render(new Rectangle(70, 10, 200, 100), graphics);
      {
        BufferedImage bufferedImage = new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
        show.render(new Rectangle(70, 10, 200, 100), bufferedImage.getGraphics());
        graphics.drawImage(bufferedImage, 300, 0, 900, 600, null);
      }
    }
  };

  public ShowDemo() {
    Tensor domain = Subdivide.increasing(Clips.unit(), 50);
    Tensor rgba = domain.map(ColorDataGradients.CLASSIC);
    show.cbb = CoordinateBoundingBox.of(Clips.unit(), Clips.interval(-2, 2));
    show.setPlotLabel(ListPlot.class.getSimpleName());
    show.add(new ListPlot(domain, rgba.get(Tensor.ALL, 0))).setLabel("red");
    show.add(new ListPlot(domain, rgba.get(Tensor.ALL, 1))).setLabel("green");
    show.add(new ListPlot(domain, rgba.get(Tensor.ALL, 2))).setLabel("blue");
    show.add(new Plot(s -> Cos.FUNCTION.apply(s.add(s)), Clips.positive(0.5))).setLabel("sine");
    jFrame.setContentPane(jComponent);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setBounds(100, 100, 1400, 900);
    jFrame.setVisible(true);
  }

  public static void main(String[] args) {
    ShowDemo showDemo = new ShowDemo();
  }
}
