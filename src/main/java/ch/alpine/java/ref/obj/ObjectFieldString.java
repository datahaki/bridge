// code by jph
package ch.alpine.java.ref.obj;

import java.util.stream.Collectors;

public enum ObjectFieldString {
  ;
  public static String of(Object object) {
    ObjectFieldList objectFieldList = ObjectFieldVisitor.of(new ObjectFieldList(), object);
    return objectFieldList.getList().stream().collect(Collectors.joining("\n"));
  }
}
