// code by jph
package ch.alpine.bridge.swing;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.FieldSelectionArray;
import ch.alpine.bridge.ref.ann.FieldSelectionCallback;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

/** @see FontDialog */
@ReflectionMarker
public class FontParam {
  @FieldSelectionCallback("names")
  public String name; // "Dialog"
  public FontStyle style; // PLAIN
  @FieldInteger
  @FieldSelectionArray({ "12", "14", "16", "18", "20", "22", "25" })
  public Scalar size; // 12

  /** @param font */
  public FontParam(Font font) {
    name = font.getName();
    style = FontStyle.values()[font.getStyle()];
    size = RealScalar.of(font.getSize());
  }

  @ReflectionMarker
  public static List<String> names() {
    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    return Arrays.stream(graphicsEnvironment.getAvailableFontFamilyNames()).collect(Collectors.toList());
  }

  /** Experimentation has shown that a font name unknown to the graphics environment
   * will cause a fallback to a known font
   * 
   * @return font as specified by this instance */
  public Font toFont() {
    return new Font(name, style.ordinal(), size.number().intValue());
  }
}
