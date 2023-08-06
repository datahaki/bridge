// code by jph
package ch.alpine.bridge.usr;

import java.util.Optional;

import javax.swing.JLabel;

import ch.alpine.bridge.ref.util.DialogFieldsEditor;
import ch.alpine.bridge.ref.util.ObjectProperties;
import ch.alpine.bridge.swing.LookAndFeels;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

enum DialogFieldsEditorBlock2Demo {
  ;
  public static class DoneCheck {
    public Tensor tensor = Tensors.vector(1, 2, 3);
  }

  public static void main(String[] args) {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    JLabel jLabel = new JLabel();
    DoneCheck doneNest = new DoneCheck();
    Optional<DoneCheck> optional = DialogFieldsEditor.block(jLabel, doneNest, "here");
    System.out.println("HERE");
    optional.ifPresent(object -> ObjectProperties.list(object).forEach(System.out::println));
    // WindowClosed.runs(dialogFieldsEditor, () -> System.out.println(dialogFieldsEditor.getSelection()));
    ObjectProperties.list(doneNest).forEach(System.out::println);
  }
}
