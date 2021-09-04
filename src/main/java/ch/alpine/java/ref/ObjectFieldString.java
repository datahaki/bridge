// code by jph
package ch.alpine.java.ref;

import java.util.stream.Collectors;

public enum ObjectFieldString {
  ;
  /** @param object
   * @return */
  public static String of(Object object) {
    return ObjectFieldList.of(object).stream().collect(Collectors.joining("\n"));
  }
}
