// code by jph
package ch.alpine.java.ref;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ch.alpine.java.ref.SimpleParam.AnotherParam;
import ch.alpine.java.ref.SimpleParam.NestedParam;
import ch.alpine.java.ref.gui.FieldsEditor;

public class FieLabParam {
  public Boolean fuse = true;
  @FieldLabels(text = { "abc" })
  public final List<NestedParam> list = Arrays.asList(new NestedParam(), new NestedParam());
  public final List<AnotherParam> another;
  public final Boolean[] fuses = new Boolean[] { true, false, true };

  public FieLabParam(int l) {
    another = new LinkedList<>();
    for (int index = 0; index < l; ++index)
      another.add(new AnotherParam());
  }

  public static void main(String[] args) {
    System.out.println(ObjectProperties.string(new FieLabParam(4)));
    FieldsEditor fieldsEditor = new FieldsEditor(new FieLabParam(4));
    fieldsEditor.getToolbarsComponent();
  }
}
