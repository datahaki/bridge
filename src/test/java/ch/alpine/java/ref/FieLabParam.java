// code by jph
package ch.alpine.java.ref;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ch.alpine.java.ref.SimpleParam.AnotherParam;
import ch.alpine.java.ref.SimpleParam.NestedParam;
import ch.alpine.java.ref.ann.FieldLabelArray;
import ch.alpine.java.ref.ann.ReflectionMarker;

@ReflectionMarker
public class FieLabParam {
  public Boolean fuse = true;
  @FieldLabelArray(text = { "abc" })
  public final List<NestedParam> list = Arrays.asList(new NestedParam(), new NestedParam());
  public final List<AnotherParam> another;
  public final Boolean[] fuses = new Boolean[] { true, false, true };

  public FieLabParam(int l) {
    another = new LinkedList<>();
    for (int index = 0; index < l; ++index)
      another.add(new AnotherParam());
  }
}
