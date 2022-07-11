// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Font;

import org.junit.jupiter.api.Test;

class FontParserTest {
  @Test
  void testFont() {
    Font font = new Font(Font.DIALOG, 1, 12);
    String string = FontParser.toString(font);
    assertTrue(string.endsWith(", BOLD, 12]"));
  }

  @Test
  void testFails() {
    assertNull(FontParser.toFont("asd"));
    assertNull(FontParser.toFont("Font[]"));
    assertNull(FontParser.toFont("Font[Some,BOLD,]"));
    assertNull(FontParser.toFont("Font[Some,,3]"));
    assertNull(FontParser.toFont("Font[,BOLD,3]"));
    assertNull(FontParser.toFont("Font[  ,BOLD,3]"));
  }
}
