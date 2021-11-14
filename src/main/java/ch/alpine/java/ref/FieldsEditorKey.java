// code by jph
package ch.alpine.java.ref;

public enum FieldsEditorKey {
  INT_STRING_PANEL_HEIGHT, //
  INT_SLIDER_HEIGHT, //
  INT_BUTTON_HEIGHT, //
  INT_LABEL_HEIGHT, //
  FONT_TEXTFIELD, //
  ICON_CHECKBOX_0, //
  ICON_CHECKBOX_1, //
  ;

  String key() {
    return getClass().getSimpleName() + "." + name();
  }
}
