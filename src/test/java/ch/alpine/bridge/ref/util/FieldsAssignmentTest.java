// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ch.alpine.bridge.ref.ex.FieldOuterParam;
import ch.alpine.bridge.ref.ex.GuiExtension;

class FieldsAssignmentTest {
  @Test
  void test() {
    Set<String> set = new HashSet<>();
    AtomicInteger atomicInteger = new AtomicInteger();
    FieldOuterParam fieldOuterParam = new FieldOuterParam();
    FieldsAssignment fieldsAssignment = FieldsAssignment.of(fieldOuterParam);
    fieldsAssignment.stream().forEach(i -> {
      atomicInteger.getAndIncrement();
      assertEquals(fieldOuterParam.nestedParam[0].text, "abc");
      assertEquals(fieldOuterParam.nestedParam[1].text, "abc");
      set.add(ObjectProperties.join(fieldOuterParam));
    });
    assertEquals(atomicInteger.get(), 2 * 96);
    assertEquals(set.size(), 2 * 96);
  }

  @Test
  void testStream() {
    FieldOuterParam fieldOuterParam = new FieldOuterParam();
    FieldsAssignment fieldsAssignment = FieldsAssignment.of(fieldOuterParam);
    Set<String> set = fieldsAssignment.stream().map(ObjectProperties::join).collect(Collectors.toSet());
    assertEquals(set.size(), 192);
  }

  static Stream<Arguments> objectStream() {
    FieldOuterParam fieldOuterParam = new FieldOuterParam();
    FieldsAssignment fieldsAssignment = FieldsAssignment.of(fieldOuterParam);
    return fieldsAssignment.stream().map(Arguments::of);
  }

  @ParameterizedTest
  @MethodSource("objectStream")
  void isBlank(FieldOuterParam fieldOuterParam) {
    String string = ObjectProperties.join(fieldOuterParam);
    string.length();
  }

  @Test
  void testLimit() {
    AtomicInteger atomicInteger = new AtomicInteger();
    FieldOuterParam fieldOuterParam = new FieldOuterParam();
    FieldsAssignment fieldsAssignment = FieldsAssignment.of(fieldOuterParam);
    fieldsAssignment.randomize(15).forEach(i -> {
      atomicInteger.getAndIncrement();
      assertEquals(fieldOuterParam.nestedParam[0].text, "abc");
      assertEquals(fieldOuterParam.nestedParam[1].text, "abc");
    });
    assertEquals(atomicInteger.get(), 15);
    fieldsAssignment.randomize(200).forEach(i -> {
      atomicInteger.getAndIncrement();
      assertEquals(fieldOuterParam.nestedParam[0].text, "abc");
      assertEquals(fieldOuterParam.nestedParam[1].text, "abc");
    });
    assertEquals(atomicInteger.get(), 15 + 192);
  }

  @Test
  void testGuiExtensions() {
    AtomicInteger atomicInteger = new AtomicInteger();
    GuiExtension guiExtension = new GuiExtension();
    FieldsAssignment fieldsAssignment = FieldsAssignment.of(guiExtension);
    fieldsAssignment.randomize(133).forEach(i -> atomicInteger.getAndIncrement());
    assertEquals(atomicInteger.get(), 133);
    fieldsAssignment.restore();
  }
}
