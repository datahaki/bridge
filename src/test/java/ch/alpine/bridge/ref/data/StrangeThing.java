// code by jph
package ch.alpine.bridge.ref.data;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.swing.LookAndFeels;

@ReflectionMarker
public class StrangeThing {
  public int dontAppear = 2;
  public final int appearNot = 3;
  public boolean b_dontAppear = true;
  public final boolean b_appearNot = true;
  public String string = "asd";

  static void main() {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    StrangeThing strangeThing = new StrangeThing();
    DialogFieldsEditor.show(null, strangeThing, null);
  }
}
