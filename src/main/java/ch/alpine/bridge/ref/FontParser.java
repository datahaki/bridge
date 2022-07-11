// code by jph
package ch.alpine.bridge.ref;

import java.awt.Font;

import ch.alpine.bridge.swing.FontStyle;

enum FontParser {
  ;
  public static String toString(Font font) {
    return String.format("Font[%s, %s, %d]", font.getName(), FontStyle.values()[font.getStyle()], font.getSize());
  }

  public static Font toFont(String string) {
    if (string.startsWith("Font[") && string.endsWith("]")) {
      string = string.substring(5, string.length() - 1);
      String[] splits = string.split(",");
      if (splits.length == 3) {
        String name = splits[0].trim();
        if (!name.isEmpty())
          try {
            FontStyle fontStyle = FontStyle.valueOf(splits[1].trim());
            int size = Integer.parseInt(splits[2].trim());
            return new Font(name, fontStyle.ordinal(), size);
          } catch (Exception exception) {
            // ---
          }
      }
    }
    return null;
  }
}
