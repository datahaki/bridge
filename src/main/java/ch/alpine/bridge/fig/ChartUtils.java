package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum ChartUtils {
  ;
  private static final Insets INSETS = new Insets(5, 70, 25, 5);

  public static void saveChartAsPNG(File file, JFreeChart jFreeChart, Dimension dimension) throws IOException {
    BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, dimension.width, dimension.height);
    Rectangle rectangle = new Rectangle( //
        INSETS.left, //
        INSETS.top, //
        dimension.width - INSETS.left - INSETS.right, //
        dimension.height - INSETS.bottom);
    jFreeChart.draw(graphics, rectangle);
    ImageIO.write(bufferedImage, "png", file);
  }
}
