// code by jph
package ch.alpine.bridge.io;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class AnsiColorTest {
  @ParameterizedTest
  @EnumSource
  void test(AnsiColor ansiColor) {
    System.out.println(ansiColor.wrap("This text is " + ansiColor));
  }
}
