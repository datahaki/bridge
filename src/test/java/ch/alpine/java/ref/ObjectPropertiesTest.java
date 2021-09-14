// code by jph
package ch.alpine.java.ref;

import java.awt.Color;
import java.io.File;
import java.util.Collections;
import java.util.Properties;

import ch.alpine.tensor.ext.HomeDirectory;
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

  public void testNull() {
    assertEquals(ObjectProperties.list(null), Collections.emptyList());
    assertEquals(ObjectProperties.string(null), "");
  }

  public void testTrySaveAndLoad() {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.nestedParams[0].some = false;
    simpleParam.nestedParams[1].text = "here!";
    simpleParam.nestedParams[0].anotherParam.color = Color.PINK;
    simpleParam.nestedParams[1].basic = false;
    File file = HomeDirectory.file(getClass().getSimpleName() + ".properties");
    assertFalse(file.exists());
    ObjectProperties.trySave(simpleParam, file);
    assertTrue(file.exists());
    SimpleParam simplePar = ObjectProperties.tryLoad(new SimpleParam(), file);
    file.delete();
    assertEquals(ObjectProperties.list(simplePar), ObjectProperties.list(simpleParam));
  }
}
