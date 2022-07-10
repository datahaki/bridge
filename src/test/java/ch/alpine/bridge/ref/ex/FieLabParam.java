// code by jph
package ch.alpine.bridge.ref.ex;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ch.alpine.bridge.ref.ann.FieldLabelArray;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.ex.SimpleParam.AnotherParam;
import ch.alpine.bridge.ref.ex.SimpleParam.NestedParam;

@ReflectionMarker
public class FieLabParam {
  public Boolean fuse = true;
  @FieldLabelArray({ "abc" })
  public final List<NestedParam> list = Arrays.asList(new NestedParam(), new NestedParam());
  public final List<AnotherParam> another;
  public final Boolean[] fuses = new Boolean[] { true, false, true };

  public FieLabParam() {
    this(3);
  }

  public FieLabParam(int l) {
    another = new LinkedList<>();
    for (int index = 0; index < l; ++index)
      another.add(new AnotherParam());
  }
}
