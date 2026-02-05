// code by jph
package showcase.data;

import java.util.LinkedList;
import java.util.List;

import ch.alpine.bridge.ref.ann.FieldLabelArray;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import showcase.data.SimpleParam.AnotherParam;
import showcase.data.SimpleParam.NestedParam;

@ReflectionMarker
public class FieLabParam {
  public Boolean fuse = true;
  @FieldLabelArray({ "abc" })
  public final List<NestedParam> list = List.of(new NestedParam(), new NestedParam());
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
