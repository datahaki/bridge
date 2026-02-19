// code by jph
package ch.alpine.bridge.ref.data;

import java.util.List;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.util.ObjectProperties;

@ReflectionMarker
public enum ReflOnEnum {
  INST1,
  INST2;

  public String here = name();

  static void main() {
    List<String> list = ObjectProperties.list(INST1);
    IO.println(list);
  }
}
