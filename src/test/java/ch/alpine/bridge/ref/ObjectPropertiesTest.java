// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.io.File;
import java.util.Collections;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.RationalScalar;

class ObjectPropertiesTest {
  @Test
  void testSimple() {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.nestedParams[1].some = false;
    simpleParam.nestedParams[1].text = "here!";
    simpleParam.nestedParams[1].anotherParam.color = Color.BLUE;
    simpleParam.nestedParams[1].basic = false;
    Properties properties = DeprecatedObjProp.properties(simpleParam);
    simpleParam = null;
    SimpleParam simpleCopy = ObjectProperties.set(new SimpleParam(), properties);
    assertEquals(simpleCopy.nestedParams[1].anotherParam.color, Color.BLUE);
    assertFalse(simpleCopy.nestedParams[1].basic);
  }

  @Test
  void testNull() {
    assertEquals(ObjectProperties.list(null), Collections.emptyList());
    assertEquals(ObjectProperties.string(null), "");
  }

  @Test
  void testTrySaveAndLoad(@TempDir File tempDir) {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.nestedParams[0].some = false;
    simpleParam.nestedParams[1].text = "here!";
    simpleParam.nestedParams[0].anotherParam.color = Color.PINK;
    simpleParam.nestedParams[1].basic = false;
    File file = new File(tempDir, "file.properties");
    assertFalse(file.exists());
    ObjectProperties.trySave(simpleParam, file);
    assertTrue(file.exists());
    SimpleParam simplePar = ObjectProperties.tryLoad(new SimpleParam(), file);
    file.delete();
    assertEquals(ObjectProperties.list(simplePar), ObjectProperties.list(simpleParam));
  }

  @Test
  void testLaram() {
    SimpleLaram simpleLaram = new SimpleLaram();
    String string0 = ObjectProperties.string(simpleLaram);
    simpleLaram.nestedParams.get(0).some = false;
    simpleLaram.nestedParams.get(1).text = "new text";
    simpleLaram.nestedParams.get(1).scalar = RationalScalar.HALF;
    String string1 = ObjectProperties.string(simpleLaram);
    Properties properties = DeprecatedObjProp.properties(simpleLaram);
    simpleLaram = null;
    SimpleLaram simpleCopy = ObjectProperties.set(new SimpleLaram(), properties);
    String string2 = ObjectProperties.string(simpleCopy);
    assertEquals(simpleCopy.nestedParams.get(1).text, "new text");
    assertEquals(simpleCopy.nestedParams.get(1).scalar, RationalScalar.HALF);
    assertFalse(string0.equals(string1));
    assertEquals(string1, string2);
  }
}
