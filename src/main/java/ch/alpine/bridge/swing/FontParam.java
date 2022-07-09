// code by jph
package ch.alpine.bridge.swing;

import java.awt.Font;

import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.FieldSelectionArray;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

@ReflectionMarker
public class FontParam {
  public FontName name = FontName.Dialog;
  public FontStyle style = FontStyle.PLAIN;
  @FieldInteger
  @FieldSelectionArray({ "12", "14", "15", "16", "17", "18" })
  public Scalar size = RealScalar.of(12);

  public FontParam(Font font) {
    name = FontName.valueOf(font.getName());
    style = FontStyle.values()[font.getStyle()];
    size = RealScalar.of(font.getSize());
  }

  public Font toFont() {
    return new Font(name.name(), style.ordinal(), size.number().intValue());
  }
}
