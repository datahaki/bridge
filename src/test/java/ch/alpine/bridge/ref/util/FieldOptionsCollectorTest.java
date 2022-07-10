// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.ex.NameString;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;

class FieldOptionsCollectorTest {
  @ReflectionMarker
  public static class Param {
    public NameString nameString;
  }

  @Test
  void testSimple() {
    Param param = new Param();
    List<NameString> list = new LinkedList<>();
    FieldsAssignment fieldsAssignment = RandomFieldsAssignment.of(param, () -> {
      list.add(param.nameString);
    });
    fieldsAssignment.forEach();
    assertEquals(list.size(), 3);
    assertEquals(list.stream().distinct().count(), 3);
  }

  @ReflectionMarker
  public static class DiscreteParam {
    @FieldInteger
    @FieldClip(min = "-10", max = "100")
    public Scalar integer;
  }

  @Test
  public void testDiscreteRandom() {
    Set<Scalar> set = new HashSet<>();
    DiscreteParam discreteParam = new DiscreteParam();
    FieldsAssignment fieldsAssignment = RandomFieldsAssignment.of(discreteParam, () -> {
      set.add(discreteParam.integer);
    });
    fieldsAssignment.forEach();
    assertEquals(set.size(), 2);
    assertTrue(set.contains(RealScalar.of(-10)));
    assertTrue(set.contains(RealScalar.of(100)));
    fieldsAssignment.randomize(20);
    assertTrue(10 < set.size());
  }

  @Test
  public void testDiscreteOuter() {
    Set<Scalar> set = new HashSet<>();
    DiscreteParam discreteParam = new DiscreteParam();
    FieldsAssignment fieldsAssignment = OuterFieldsAssignment.of(discreteParam, () -> {
      set.add(discreteParam.integer);
    });
    fieldsAssignment.forEach();
    assertEquals(set.size(), 2);
    assertTrue(set.contains(RealScalar.of(-10)));
    assertTrue(set.contains(RealScalar.of(100)));
  }

  @Test
  void test() {
    assertFalse(Modifier.isPublic(FieldOptionsCollector.class.getModifiers()));
  }
}
