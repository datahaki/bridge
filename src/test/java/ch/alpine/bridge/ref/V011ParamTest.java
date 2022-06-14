// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class V011ParamTest {
  @Test
  void testSimple() {
    V011Param v011Param = new V011Param(3);
    v011Param.list.set(1, null);
    v011Param.another.set(1, null);
    ObjectProperties.list(v011Param);
    ObjectProperties.save(v011Param);
    // System.out.println();
    Properties properties = DeprecatedObjProp.properties(v011Param);
    ObjectProperties.set(new V011Param(1), properties);
    ObjectProperties.set(new V011Param(2), properties);
  }

  @Test
  void testFromString() throws IOException {
    V011Param v011Param1 = new V011Param(2);
    v011Param1.anotherParam.file = new File("c:\\windows\\here.txt");
    v011Param1.string = "abc\u00a3 here more\njaja\tasd";
    String string = ObjectProperties.save(v011Param1);
    V011Param v011Param2 = new V011Param(2);
    ObjectProperties.load(v011Param2, string);
    assertTrue(ObjectFields.deepEquals(v011Param1, v011Param2));
  }

  @Test
  void testSaveLoad(@TempDir File folder) throws IOException {
    V011Param v011Param1 = new V011Param(3);
    v011Param1.anotherParam.file = new File("c:\\windows\\here.txt");
    v011Param1.string = "abc\u00a3 here more\njaja\tasd\u3000special";
    File file = new File(folder, "export.properties");
    ObjectProperties.save(v011Param1, file);
    V011Param v011Param2 = new V011Param(3);
    ObjectProperties.load(v011Param2, file);
    assertTrue(ObjectFields.deepEquals(v011Param1, v011Param2));
  }
}
