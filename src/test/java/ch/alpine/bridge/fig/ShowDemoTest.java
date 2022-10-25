package ch.alpine.bridge.fig;

import java.awt.Dimension;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ShowDemoTest {
  @ParameterizedTest
  @EnumSource
  void test(ShowDemos showDemos) {
    showDemos.create().image(new Dimension(400, 300));
  }
}
