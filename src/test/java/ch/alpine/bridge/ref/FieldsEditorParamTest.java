// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Font;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

class FieldsEditorParamTest {
  public static FontFieldWrap getFontFieldWrap() {
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(FieldsEditorParam.GLOBAL);
    List<FieldPanel> list = panelFieldsEditor.list();
    Optional<FieldPanel> optional = list.stream().filter(fp -> fp instanceof FontPanel).findFirst();
    FieldPanel fieldPanel = optional.orElseThrow();
    FieldWrap fieldWrap = fieldPanel.fieldWrap();
    return (FontFieldWrap) fieldWrap;
  }

  @Test
  void testFont() {
    FontFieldWrap fontFieldWrap = getFontFieldWrap();
    Font font = new Font(Font.DIALOG, 1, 12);
    String string = fontFieldWrap.toString(font);
    assertTrue(string.endsWith(", BOLD, 12]"));
  }

  @Test
  void testFails() {
    FontFieldWrap fontFieldWrap = getFontFieldWrap();
    assertNull(fontFieldWrap.toValue("asd"));
    assertNull(fontFieldWrap.toValue("Font[]"));
    assertNull(fontFieldWrap.toValue("Font[Some,BOLD,]"));
    assertNull(fontFieldWrap.toValue("Font[Some,,3]"));
    assertNull(fontFieldWrap.toValue("Font[,BOLD,3]"));
    assertNull(fontFieldWrap.toValue("Font[  ,BOLD,3]"));
  }
}
