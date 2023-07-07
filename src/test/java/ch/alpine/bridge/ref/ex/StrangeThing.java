package ch.alpine.bridge.ref.ex;

import java.util.List;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.ref.util.ObjectProperties;
import ch.alpine.bridge.swing.LookAndFeels;

@ReflectionMarker
public class StrangeThing {
  public int dontAppear = 2;
  public final int appearNot = 3;
  public String string = "asd";
  
  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    StrangeThing strangeThing = new StrangeThing();
//    List<String> list = ObjectProperties.list(strangeThing);
    DialogFieldsEditor.show(null, strangeThing, null);
//    System.out.println(list);
  }
}
