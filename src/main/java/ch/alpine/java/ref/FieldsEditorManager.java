// code by jph
package ch.alpine.java.ref;

import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JComponent;

public enum FieldsEditorManager {
  INSTANCE;

  private final Map<String, Object> map = new HashMap<>();

  private FieldsEditorManager() {
    Font font = new Font(Font.DIALOG_INPUT, Font.PLAIN, 18);
    set(FieldsEditorKey.FONT_ENUM_PANEL, font);
    set(FieldsEditorKey.FONT_STRING_PANEL, font);
  }

  public void set(FieldsEditorKey fieldsEditorKey, Object object) {
    map.put(fieldsEditorKey.key(), object);
  }

  public int getInt(FieldsEditorKey fieldsEditorKey) {
    Object object = map.get(fieldsEditorKey.key());
    return object instanceof Integer ? (Integer) object : 0;
  }

  public String getString(FieldsEditorKey fieldsEditorKey) {
    Object object = map.get(fieldsEditorKey.key());
    return object instanceof String ? (String) object : null;
  }

  public void establish(FieldsEditorKey fieldsEditorKey, JComponent jComponent) {
    int height = getInt(fieldsEditorKey);
    if (0 < height) {
      Dimension dimension = jComponent.getPreferredSize();
      dimension.height = height;
      jComponent.setPreferredSize(dimension);
    }
  }

  public Icon getIcon(FieldsEditorKey fieldsEditorKey) {
    Object object = map.get(fieldsEditorKey.key());
    return object instanceof Icon ? (Icon) object : null;
  }

  public Font getFont(FieldsEditorKey fieldsEditorKey) {
    Object object = map.get(fieldsEditorKey.key());
    return object instanceof Font ? (Font) object : null;
  }
}
