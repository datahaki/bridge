// code by jph
package ch.alpine.bridge.ref.ex;

import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.swing.FontStyle;
import ch.alpine.tensor.mat.re.Pivots;

public class TemplateEnumParam {
  public Pivots _pivots = Pivots.ARGMAX_ABS;
  public FontStyle _fontStyle = FontStyle.BOLD;
  public Enum<?> _enum1 = Pivots.ARGMAX_ABS;
  public Enum<?> _enum2 = FontStyle.BOLD;
  public Object _object1 = Pivots.ARGMAX_ABS;
  public Object _object2 = FontStyle.BOLD;

  public static void main(String[] args) {
    TemplateEnumParam templateEnumParam = new TemplateEnumParam();
    DialogFieldsEditor.show(null, "title", templateEnumParam);
  }
}
