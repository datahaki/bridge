// code by jph
package ch.alpine.bridge.ref;

import java.util.Properties;

import org.junit.jupiter.api.Test;

class V011ParamTest {
  @Test
  public void testSimple() {
    V011Param v011Param = new V011Param(3);
    v011Param.list.set(1, null);
    v011Param.another.set(1, null);
    ObjectProperties.list(v011Param);
    ObjectProperties.string(v011Param);
    // System.out.println();
    Properties properties = ObjectProperties.properties(v011Param);
    ObjectProperties.set(new V011Param(1), properties);
    ObjectProperties.set(new V011Param(2), properties);
  }
}
