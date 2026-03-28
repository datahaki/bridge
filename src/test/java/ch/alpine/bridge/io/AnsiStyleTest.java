// code by jph
package ch.alpine.bridge.io;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class AnsiStyleTest {
  @ParameterizedTest
  @EnumSource
  void test(AnsiStyle ansiStyle) {
    System.out.println(ansiStyle.wrap("This text is " + ansiStyle));
  }
}
