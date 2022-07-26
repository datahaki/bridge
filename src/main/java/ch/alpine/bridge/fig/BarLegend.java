// code by jph
package ch.alpine.bridge.fig;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Map.Entry;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.lang.Unicode;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/BarLegend.html">BarLegend</a> */
public class BarLegend {
  public static BarLegend of(ScalarTensorFunction colorDataGradient, Clip clip, Set<Scalar> set) {
    return new BarLegend(colorDataGradient, clip, set.stream().collect(Collectors.toMap(s -> s, Unicode::valueOf)));
  }

  private final ScalarTensorFunction colorDataGradient;
  private final Clip clip;
  private final Map<Scalar, String> map;
  public int space = 2;
  public Font font = new Font(Font.DIALOG, Font.PLAIN, 12);
  public Color color = Color.DARK_GRAY;

  public BarLegend(ScalarTensorFunction colorDataGradient, Clip clip, Map<Scalar, String> map) {
    this.colorDataGradient = colorDataGradient;
    this.clip = clip;
    this.map = map;
  }

  /** @param dimension of color gradient to which space is added for the labels
   * @return */
  public BufferedImage createImage(Dimension dimension) {
    int width = dimension.width;
    int height = dimension.height;
    FontMetrics fontMetrics = new Canvas().getFontMetrics(font);
    OptionalInt optionalInt = map.values().stream().mapToInt(fontMetrics::stringWidth).max();
    int maxWidth = optionalInt.isPresent() //
        ? space + optionalInt.getAsInt()
        : 0;
    BufferedImage bufferedImage = new BufferedImage(width + maxWidth, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    graphics.drawImage( //
        ImageFormat.of(Subdivide.decreasing(Clips.unit(), height - 1).map(Tensors::of).map(colorDataGradient)), //
        0, //
        0, //
        width, //
        height, null);
    graphics.setFont(font);
    graphics.setColor(color);
    RenderQuality.setQuality(graphics);
    int ascent = fontMetrics.getAscent();
    for (Entry<Scalar, String> entry : map.entrySet()) {
      Scalar rescale = RealScalar.ONE.subtract(clip.rescale(entry.getKey()));
      int piy = (int) (height * rescale.number().doubleValue() + ascent / 2);
      piy = Integers.clip(ascent, height).applyAsInt(piy);
      // piy = Math.min(Math.max(ascent, piy), height);
      graphics.drawString(entry.getValue(), width + space, piy);
    }
    graphics.dispose();
    return bufferedImage;
  }
}
