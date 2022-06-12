package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

class FieldsEditorManagerTest {
  @Test
  void testArea() {
    Font font = new JTextArea().getFont();
    assertEquals(font.getSize(), 12);
  }

  @Test
  void testField() {
    Font font = new JTextField().getFont();
    assertEquals(font.getSize(), 12);
  }
}
