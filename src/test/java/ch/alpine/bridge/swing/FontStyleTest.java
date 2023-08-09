package ch.alpine.bridge.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FontStyleTest {
  @Test
  void test() {
    assertEquals(FontStyle.values().length, 4);
  }
}
