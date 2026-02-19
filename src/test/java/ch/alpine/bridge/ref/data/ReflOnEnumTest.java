// code by jph
package ch.alpine.bridge.ref.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ch.alpine.bridge.ref.util.ObjectProperties;

class ReflOnEnumTest {
  @ParameterizedTest
  @EnumSource
  void test(ReflOnEnum reflOnEnum) {
    List<String> list = ObjectProperties.list(reflOnEnum);
    assertEquals(list.get(0), "here=" + reflOnEnum.name());
  }
}
