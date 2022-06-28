package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SliderFailParamTest {
  @Test
  void test() {
    SliderFailParam sliderFailParam = new SliderFailParam();
    assertThrows(Exception.class, () -> new PanelFieldsEditor(sliderFailParam));
  }
}
