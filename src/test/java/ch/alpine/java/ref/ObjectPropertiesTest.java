// code by jph
package ch.alpine.java.ref;

import java.awt.Color;
import java.util.Properties;

import junit.framework.TestCase;

public class ObjectPropertiesTest extends TestCase {
  public void testSimple() {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.nestedParams[1].some = false;
    simpleParam.nestedParams[1].text = "here!";
    simpleParam.nestedParams[1].anotherParam.color = Color.BLUE;
    simpleParam.nestedParams[1].basic = false;
    Properties properties = ObjectProperties.properties(simpleParam);
    simpleParam = null;
    SimpleParam simpleCopy = ObjectProperties.set(new SimpleParam(), properties);
    assertEquals(simpleCopy.nestedParams[1].anotherParam.color, Color.BLUE);
    assertFalse(simpleCopy.nestedParams[1].basic);
  }
}
