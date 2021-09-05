// code by jph
package ch.alpine.java.ref;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ch.alpine.java.ref.SimpleParam.AnotherParam;
import ch.alpine.java.ref.SimpleParam.NestedParam;

public class V011Param {
  public Boolean fuse = true;
  public final List<NestedParam> list = Arrays.asList(new NestedParam(), new NestedParam());
  public final List<AnotherParam> another;
  public final Boolean[] fuses = new Boolean[] { true, false, true };

  public V011Param(int l) {
    another = new LinkedList<>();
    for (int index = 0; index < l; ++index)
      another.add(new AnotherParam());
  }

  public static void main(String[] args) {
    System.out.println(ObjectProperties.string(new V011Param(4)));
  }
}
