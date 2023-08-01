// code by jph
package ch.alpine.bridge.usr;

import java.util.Optional;

import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.ref.util.ObjectProperties;
import ch.alpine.bridge.swing.LookAndFeels;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.re.Pivots;

enum DialogFieldsEditorBlockDemo {
  ;
  public static class DoneNest {
    public final DoneCheck doneCheck = new DoneCheck();
  }

  public static class DoneCheck {
    public Integer integer = 3;
    public Pivots pivots = Pivots.ARGMAX_ABS;
    public Tensor tensor = Tensors.vector(1, 2, 3);
    public Boolean status = false;
  }

  public static void main(String[] args) {
    LookAndFeels.DARK.updateComponentTreeUI();
    DoneNest doneNest = new DoneNest();
    Optional<DoneNest> optional = DialogFieldsEditor.block(null, doneNest, "here");
    System.out.println("HERE");
    optional.ifPresent(object -> ObjectProperties.list(object).forEach(System.out::println));
    // WindowClosed.runs(dialogFieldsEditor, () -> System.out.println(dialogFieldsEditor.getSelection()));
    ObjectProperties.list(doneNest).forEach(System.out::println);
  }
}
