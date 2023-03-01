// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ann.ReflectionMarker;
import ch.alpine.bridge.ref.ex.ColorParam;
import ch.alpine.bridge.ref.ex.FieldOuterParam;
import ch.alpine.bridge.ref.ex.FieldOuterParam.NestedParam;
import ch.alpine.tensor.Scalar;

class RandomFieldsAssignmentTest {
  @ReflectionMarker
  public static class Holder {
    public final Object[] objects;

    public Holder(Object... objects) {
      this.objects = objects;
    }
  }

  @Test
  void dynamic() {
    Holder holder = new Holder(new ColorParam(), new ColorParam());
    AtomicInteger atomicInteger = new AtomicInteger();
    FieldsAssignment fieldsAssignment = RandomFieldsAssignment.of(holder);
    fieldsAssignment.stream().forEach(i -> atomicInteger.getAndIncrement());
    assertEquals(atomicInteger.get(), 1);
  }

  @Test
  void dynamicEmpty() {
    Holder holder = new Holder();
    AtomicInteger atomicInteger = new AtomicInteger();
    FieldsAssignment fieldsAssignment = RandomFieldsAssignment.of(holder);
    fieldsAssignment.stream().forEach(i -> atomicInteger.getAndIncrement());
    assertEquals(atomicInteger.get(), 1);
  }

  @Test
  void dynamicEmpty2() {
    Holder holder = new Holder();
    AtomicInteger atomicInteger = new AtomicInteger();
    FieldsAssignment fieldsAssignment = RandomFieldsAssignment.of(holder);
    fieldsAssignment.randomize(10).forEach(i -> atomicInteger.getAndIncrement());
    assertEquals(atomicInteger.get(), 1);
  }

  @Test
  void test() {
    Set<Scalar> set = new HashSet<>();
    FieldOuterParam fieldOuterParam = new FieldOuterParam();
    FieldsAssignment fieldsAssignment = RandomFieldsAssignment.of(fieldOuterParam);
    assertEquals(set.size(), 0);
    fieldsAssignment.randomize(300).forEach(i -> set.add(fieldOuterParam.ratio));
    assertTrue(100 < set.size());
  }

  @Test
  void testGrid() {
    AtomicInteger atomicInteger = new AtomicInteger();
    NestedParam nestedParam = new NestedParam();
    FieldsAssignment fieldsAssignment = RandomFieldsAssignment.of(nestedParam);
    assertEquals(atomicInteger.get(), 0);
    fieldsAssignment.randomize(2).forEach(i -> atomicInteger.getAndIncrement());
    assertEquals(atomicInteger.get(), 2);
    fieldsAssignment.randomize(10).forEach(i -> atomicInteger.getAndIncrement());
    assertEquals(atomicInteger.get(), 2 + 4);
  }

  @Test
  void testColor() {
    Set<Color> set_color = new HashSet<>();
    Set<LocalTime> set_time = new HashSet<>();
    ColorParam colorParam = new ColorParam();
    FieldsAssignment fieldsAssignment = RandomFieldsAssignment.of(colorParam);
    Runnable runnable = () -> {
      set_color.add(colorParam.color);
      set_time.add(colorParam.localTime);
    };
    assertEquals(set_color.size(), 0);
    assertEquals(set_time.size(), 0);
    int n = 20;
    RandomGenerator random = new Random(3);
    fieldsAssignment.randomize(random, n).forEach(i -> runnable.run());
    assertEquals(set_color.size(), n);
    assertEquals(set_time.size(), n);
    random = new Random(3);
    fieldsAssignment.randomize(random, n).forEach(i -> runnable.run());
    assertEquals(set_color.size(), n);
    assertEquals(set_time.size(), n);
  }
}
