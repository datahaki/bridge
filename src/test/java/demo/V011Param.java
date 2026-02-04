// code by jph
package demo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.ObjectProperties;
import demo.SimpleParam.AnotherParam;
import demo.SimpleParam.NestedParam;

@ReflectionMarker
public class V011Param {
  public Boolean fuse = true;
  public final List<NestedParam> list = Arrays.asList(new NestedParam(), new NestedParam());
  public final List<AnotherParam> another;
  public final AnotherParam anotherParam = new AnotherParam();
  public String string = "abc";
  public final Boolean[] fuses = new Boolean[] { true, false, true };

  public V011Param(int l) {
    another = new LinkedList<>();
    for (int index = 0; index < l; ++index)
      another.add(new AnotherParam());
  }

  static void main() {
    System.out.println(ObjectProperties.join(new V011Param(4)));
  }
}
