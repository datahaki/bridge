// code by jph
package ch.alpine.bridge.ref;

import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.UIManager;

import ch.alpine.bridge.ref.util.FieldsEditor;

/** FieldsEditorManager manages additional keys to specify the layout of instances of
 * {@link FieldsEditor} that are not already covered by {@link UIManager}.
 * 
 * @see FieldsEditorKey */
public enum FieldsEditorManager {
  ;
  private static final Map<String, Object> MAP = new HashMap<>();
  static {
    set(FieldsEditorKey.FONT_TEXTFIELD, new Font(Font.DIALOG_INPUT, Font.PLAIN, 12));
    set(FieldsEditorKey.INT_TOOLBAR_COMPONENT_HEIGHT, 28);
  }

  public static void set(FieldsEditorKey fieldsEditorKey, Object object) {
    MAP.put(fieldsEditorKey.key(), object);
  }

  public static Integer getInteger(FieldsEditorKey fieldsEditorKey) {
    Object object = MAP.get(fieldsEditorKey.key());
    return object instanceof Integer integer ? integer : null;
  }

  public static String getString(FieldsEditorKey fieldsEditorKey) {
    Object object = MAP.get(fieldsEditorKey.key());
    return object instanceof String string ? string : null;
  }

  public static Icon getIcon(FieldsEditorKey fieldsEditorKey) {
    Object object = MAP.get(fieldsEditorKey.key());
    return object instanceof Icon icon ? icon : null;
  }

  public static Font getFont(FieldsEditorKey fieldsEditorKey) {
    Object object = MAP.get(fieldsEditorKey.key());
    return object instanceof Font font ? font : null;
  }

  // ---
  public static void setHeight(FieldsEditorKey fieldsEditorKey, JComponent jComponent) {
    Integer height = getInteger(fieldsEditorKey);
    if (Objects.nonNull(height)) {
      Dimension dimension = jComponent.getPreferredSize();
      dimension.height = height;
      jComponent.setPreferredSize(dimension);
    }
  }

  public static void maxHeight(JComponent jComponent) {
    Integer height = getInteger(FieldsEditorKey.INT_TOOLBAR_COMPONENT_HEIGHT);
    if (Objects.nonNull(height)) {
      Dimension dimension = jComponent.getPreferredSize();
      dimension.height = Math.max(dimension.height, height);
      jComponent.setPreferredSize(dimension);
    }
  }
}
