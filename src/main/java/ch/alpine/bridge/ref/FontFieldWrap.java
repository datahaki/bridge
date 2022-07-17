// code by jph
package ch.alpine.bridge.ref;

import java.awt.Font;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import ch.alpine.bridge.swing.FontStyle;

/** for strings that can be parsed into {@link Font}, for instance
 * Font[DialogInput, PLAIN, 15] */
/* package */ class FontFieldWrap extends BaseFieldWrap {
  public FontFieldWrap(Field field) {
    super(field);
  }

  @Override // from FieldWrap
  public Font toValue(String string) {
    Objects.requireNonNull(string);
    if (string.startsWith("Font[") && string.endsWith("]")) {
      string = string.substring(5, string.length() - 1);
      String[] splits = string.split(",");
      if (splits.length == 3 && string.chars().filter(chr -> chr == ',').count() == 2) {
        String name = splits[0].trim();
        if (!name.isEmpty())
          try {
            FontStyle fontStyle = FontStyle.valueOf(splits[1].trim());
            int size = Integer.parseInt(splits[2].trim());
            if (0 <= size)
              return new Font(name, fontStyle.ordinal(), size);
          } catch (Exception exception) {
            // ---
          }
      }
    }
    return null;
  }

  @Override // from FieldWrap
  public String toString(Object value) {
    Font font = (Font) value;
    return String.format("Font[%s, %s, %d]", font.getName(), FontStyle.values()[font.getStyle()], font.getSize());
  }

  @Override // from FieldWrap
  public List<Object> options(Object object) {
    return List.of();
  }

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    return new FontPanel(this, (Font) value);
  }
}
