// code by jph
package sys.col;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

import javax.swing.JLabel;

// TODO MIDI class design is immature
public final class ImageConvert {
  public static BufferedImage toType(Image image, int type) {
    JLabel jLabel = new JLabel();
    Dimension dimension = new Dimension(image.getWidth(jLabel), image.getHeight(jLabel));
    BufferedImage bufferedImage = new BufferedImage(dimension.width, dimension.height, type);
    Graphics2D graphics = bufferedImage.createGraphics();
    graphics.drawImage(image, 0, 0, jLabel);
    graphics.dispose();
    return bufferedImage;
  }

  public static BiFunction<Point, Integer, Integer> reduceIntensity(double factor) {
    return (_, rgba) -> {
      int rgb = rgba & 0xffffff;
      int a = (rgba >> 24) & 0xff;
      a = (int) Math.round(a * factor);
      return (a << 24) + rgb;
    };
  }

  public static BiFunction<Point, Integer, Integer> modifHSV(double s, double v) {
    return (_, rgba) -> HueFromColor.of(new Color(rgba, true)).modifHSV(s, v).getRGB();
  }

  public static BiFunction<Point, Integer, Integer> makeWhiteTransparent(double mult) {
    return (_, rgba) -> {
      HueFromColor hueFromColor = HueFromColor.of(new Color(rgba, true));
      int rgb = rgba & 0xffffff;
      int a = (int) Math.min(Math.max(0, (1 + hueFromColor.sat() - mult * hueFromColor.val()) * 255), 255);
      return (a << 24) + rgb;
    };
  }

  public static BiFunction<Point, Integer, Integer> setTransparency(double alpha) {
    final int a = (int) Math.min(Math.max(0, alpha * 255), 255);
    return (_, rgba) -> {
      int rgb = rgba & 0xffffff;
      return (a << 24) + rgb;
    };
  }

  public static BufferedImage apply(Image image, BiFunction<Point, Integer, Integer> biFunction) {
    ImageConvert imageConvert = new ImageConvert(image);
    imageConvert.apply(biFunction);
    return imageConvert.bufferedImage;
  }

  public static BufferedImage reduceIntensity(Image image, double factor) {
    return apply(image, reduceIntensity(factor));
  }

  public static BufferedImage modifHSV(Image image, double s, double v) {
    return apply(image, modifHSV(s, v));
  }

  // ---
  private final BufferedImage bufferedImage;

  public ImageConvert(Image image) {
    bufferedImage = toType(image, BufferedImage.TYPE_INT_ARGB);
  }

  public void apply(BiFunction<Point, Integer, Integer> biFunction) {
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    for (int x = 0; x < width; ++x)
      for (int y = 0; y < height; ++y)
        bufferedImage.setRGB(x, y, biFunction.apply(new Point(x, y), bufferedImage.getRGB(x, y)));
  }

  public BufferedImage toType(int type) {
    return toType(bufferedImage, type);
  }

  public BufferedImage getBufferedImage() {
    return bufferedImage;
  }
}
