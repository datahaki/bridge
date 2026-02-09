package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SanitizeFilenameTest {
  @Test
  void test() {
    String string = SanitizeFilename.of(" .a12sd  //?* . txt  ");
    assertEquals(string, " .a12sd  ____ . txt");
  }
}
