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
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.bridge.ref.Cacheables;
import ch.alpine.bridge.ref.data.ClipParam;
import ch.alpine.bridge.ref.data.GuiExtension;
import ch.alpine.bridge.ref.data.ParamContainer;
import ch.alpine.bridge.ref.data.ParamContainerExt;
import ch.alpine.bridge.ref.data.SimpleLaram;
import ch.alpine.bridge.ref.data.SimpleParam;
import ch.alpine.bridge.ref.data.TimeParam;
import ch.alpine.bridge.ref.data.V011Param;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.ResourceData;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clips;

class ObjectPropertiesTest {
  @TempDir
  Path tempDir;

  @Test
  void testSimple() throws IOException {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.nestedParams[1].some = false;
    simpleParam.nestedParams[1].text = "here!";
    simpleParam.nestedParams[1].anotherParam.color = Color.BLUE;
    simpleParam.nestedParams[1].basic = false;
    Path file = tempDir.resolve("hereSimple.properties");
    ObjectProperties.save(simpleParam, file);
    simpleParam = null;
    SimpleParam simpleCopy = new SimpleParam();
    ObjectProperties.load(simpleCopy, file);
    assertEquals(simpleCopy.nestedParams[1].anotherParam.color, Color.BLUE);
    assertFalse(simpleCopy.nestedParams[1].basic);
  }

  @Test
  void testNull() {
    assertEquals(ObjectProperties.list(null), Collections.emptyList());
    assertEquals(ObjectProperties.join(null), "");
  }

  @Test
  void testTrySaveAndLoad() throws IOException {
    SimpleParam simpleParam = new SimpleParam();
    simpleParam.nestedParams[0].some = false;
    simpleParam.nestedParams[1].text = "here!";
    simpleParam.nestedParams[0].anotherParam.color = Color.PINK;
    simpleParam.nestedParams[1].basic = false;
    Path file = tempDir.resolve("fileTryAndSave.properties");
    assertFalse(Files.exists(file));
    ObjectProperties.trySave(simpleParam, file);
    assertTrue(Files.exists(file));
    SimpleParam simplePar = ObjectProperties.tryLoad(new SimpleParam(), file);
    Files.delete(file);
    assertEquals(ObjectProperties.list(simplePar), ObjectProperties.list(simpleParam));
  }

  @Test
  void testTrySaveFail() {
    SimpleParam simpleParam = new SimpleParam();
    assertFalse(ObjectProperties.trySave(simpleParam, Path.of("/should not be possible/to save/here")));
  }

  @Test
  void testLaram() throws IOException {
    SimpleLaram simpleLaram = new SimpleLaram();
    String string0 = ObjectProperties.join(simpleLaram);
    simpleLaram.nestedParams.get(0).some = false;
    simpleLaram.nestedParams.get(1).text = "new text";
    simpleLaram.nestedParams.get(1).scalar = Rational.HALF;
    String string1 = ObjectProperties.join(simpleLaram);
    Path file = tempDir.resolve("here2.properties");
    ObjectProperties.save(simpleLaram, file);
    simpleLaram = null;
    SimpleLaram simpleCopy = new SimpleLaram();
    ObjectProperties.load(simpleCopy, file);
    String string2 = ObjectProperties.join(simpleCopy);
    assertEquals(simpleCopy.nestedParams.get(1).text, "new text");
    assertEquals(simpleCopy.nestedParams.get(1).scalar, Rational.HALF);
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
      ResourceData.properties("ch/alpine/bridge/io/ParamContainer.properties"));

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
    assertTrue(Cacheables.deepEquals(v011Param1, v011Param2));
  }

  @Test
  void testSaveLoad() throws IOException {
    V011Param v011Param1 = new V011Param(3);
    v011Param1.anotherParam.file = new File("c:\\windows\\here.txt");
    v011Param1.string = "abc\u00a3 here more\njaja\tasd\u3000special";
    Path file = tempDir.resolve("exportSaveLoad.properties");
    ObjectProperties.save(v011Param1, file);
    V011Param v011Param2 = new V011Param(3);
    ObjectProperties.load(v011Param2, file);
    assertTrue(Cacheables.deepEquals(v011Param1, v011Param2));
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
  void testTimeParam() throws IOException {
    TimeParam timeParam = new TimeParam();
    timeParam.dateTime = LocalDateTime.of(2020, 1, 1, 0, 0);
    timeParam.date = LocalDate.of(1923, 12, 31);
    timeParam.time = LocalTime.of(23, 59, 33);
    Path file = tempDir.resolve("fileTimeParam.properties");
    ObjectProperties.save(timeParam, file);
    timeParam = new TimeParam();
    ObjectProperties.load(timeParam, file);
    assertEquals(timeParam.dateTime.toString(), "2020-01-01T00:00");
    assertEquals(timeParam.date.toString(), "1923-12-31");
    assertEquals(timeParam.time.toString(), "23:59:33");
  }

  @Test
  void testSimple2() throws IOException {
    V011Param v011Param = new V011Param(3);
    v011Param.list.set(1, null);
    v011Param.another.set(1, null);
    ObjectProperties.list(v011Param);
    ObjectProperties.join(v011Param);
    Path file = tempDir.resolve("here4.properties");
    ObjectProperties.save(v011Param, file);
    ObjectProperties.load(new V011Param(1), file);
    ObjectProperties.load(new V011Param(2), file);
  }

  @Test
  void testRecreate() throws IOException {
    ClipParam clipParam = new ClipParam();
    clipParam.clipReal = Clips.interval(9, 10);
    Path file = tempDir.resolve("here3.properties");
    ObjectProperties.save(clipParam, file);
    ClipParam clipParam2 = new ClipParam();
    ObjectProperties.load(clipParam2, file);
    assertEquals(clipParam2.clipReal, Clips.interval(9, 10));
  }

  @Test
  void testGetMethodFail() {
    assertThrows(Exception.class, () -> GuiExtension.class.getMethod("stringValues2"));
  }
}
