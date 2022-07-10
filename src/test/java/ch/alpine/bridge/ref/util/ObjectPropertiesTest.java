// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.bridge.ref.ex.ClipParam;
import ch.alpine.bridge.ref.ex.GuiExtension;
import ch.alpine.bridge.ref.ex.ParamContainer;
import ch.alpine.bridge.ref.ex.ParamContainerExt;
import ch.alpine.bridge.ref.ex.SimpleLaram;
import ch.alpine.bridge.ref.ex.SimpleParam;
import ch.alpine.bridge.ref.ex.TimeParam;
import ch.alpine.bridge.ref.ex.V011Param;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.io.ResourceData;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clips;

class ObjectPropertiesTest {
  @Test
  void testSimple() {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.nestedParams[1].some = false;
    simpleParam.nestedParams[1].text = "here!";
    simpleParam.nestedParams[1].anotherParam.color = Color.BLUE;
    simpleParam.nestedParams[1].basic = false;
    Properties properties = ObjectPropertiesExt.properties(simpleParam);
    simpleParam = null;
    SimpleParam simpleCopy = ObjectProperties.set(new SimpleParam(), properties);
    assertEquals(simpleCopy.nestedParams[1].anotherParam.color, Color.BLUE);
    assertFalse(simpleCopy.nestedParams[1].basic);
  }

  @Test
  void testNull() {
    assertEquals(ObjectProperties.list(null), Collections.emptyList());
    assertEquals(ObjectProperties.join(null), "");
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
    String string0 = ObjectProperties.join(simpleLaram);
    simpleLaram.nestedParams.get(0).some = false;
    simpleLaram.nestedParams.get(1).text = "new text";
    simpleLaram.nestedParams.get(1).scalar = RationalScalar.HALF;
    String string1 = ObjectProperties.join(simpleLaram);
    Properties properties = ObjectPropertiesExt.properties(simpleLaram);
    simpleLaram = null;
    SimpleLaram simpleCopy = ObjectProperties.set(new SimpleLaram(), properties);
    String string2 = ObjectProperties.join(simpleCopy);
    assertEquals(simpleCopy.nestedParams.get(1).text, "new text");
    assertEquals(simpleCopy.nestedParams.get(1).scalar, RationalScalar.HALF);
    assertFalse(string0.equals(string1));
    assertEquals(string1, string2);
  }

  @Test
  void testClipSimple() {
    ClipParam clipParam = new ClipParam();
    List<String> list = ObjectProperties.list(clipParam);
    assertTrue(list.contains("clipReal={2, 3}"));
    assertTrue(list.contains("clipMeter={-1[m], 1[m]}"));
  }

  @Test
  void testClipModify() {
    ClipParam clipParam = new ClipParam();
    clipParam.clipReal = Clips.interval(10, 11);
    List<String> list = ObjectProperties.list(clipParam);
    assertTrue(list.contains("clipReal={10, 11}"));
    assertTrue(list.contains("clipMeter={-1[m], 1[m]}"));
  }

  public static final ParamContainer INSTANCE = ObjectProperties.set(new ParamContainer(),
      ResourceData.properties("/ch/alpine/bridge/io/ParamContainer.properties"));

  @Test
  void testParamContainerSimple() {
    ParamContainer paramContainer = INSTANCE;
    assertInstanceOf(Quantity.class, paramContainer.maxTor);
    assertEquals(paramContainer.shape.length(), 4);
  }

  @Test
  void testParamContainerExt() {
    ParamContainerExt paramContainerExt = ParamContainerExt.INSTANCE_EXT;
    assertEquals(paramContainerExt.onlyInExt, Tensors.vector(9, 7));
  }

  @Test
  void testFromString() {
    V011Param v011Param1 = new V011Param(2);
    v011Param1.anotherParam.file = new File("c:\\windows\\here.txt");
    v011Param1.string = "abc\u00a3 here more\njaja\tasd";
    String string = ObjectProperties.join(v011Param1);
    V011Param v011Param2 = new V011Param(2);
    ObjectProperties.part(v011Param2, string);
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

  @Test
  void testReader() throws IOException {
    GuiExtension guiExtension = new GuiExtension();
    String string = ObjectProperties.join(guiExtension);
    Properties properties = new Properties();
    try (StringReader stringReader = new StringReader(string)) {
      properties.load(stringReader);
    }
    assertTrue(23 < properties.entrySet().size());
  }

  @Test
  void testTimeParam(@TempDir File folder) throws IOException {
    TimeParam timeParam = new TimeParam();
    timeParam.dateTime = LocalDateTime.of(2020, 1, 1, 0, 0);
    timeParam.date = LocalDate.of(1923, 12, 31);
    timeParam.time = LocalTime.of(23, 59, 33);
    File file = new File(folder, "file.properties");
    ObjectProperties.save(timeParam, file);
    timeParam = new TimeParam();
    ObjectProperties.load(timeParam, file);
    assertEquals(timeParam.dateTime.toString(), "2020-01-01T00:00");
    assertEquals(timeParam.date.toString(), "1923-12-31");
    assertEquals(timeParam.time.toString(), "23:59:33");
  }

  @Test
  void testGetMethodFail() {
    assertThrows(Exception.class, () -> GuiExtension.class.getMethod("stringValues2"));
  }
}
