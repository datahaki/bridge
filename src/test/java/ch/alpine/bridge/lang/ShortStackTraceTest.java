package ch.alpine.bridge.lang;

import org.junit.jupiter.api.Test;

class ShortStackTraceTest {
  @Test
  void test() {
    ShortStackTrace shortStackTrace = new ShortStackTrace("ch");
    shortStackTrace.print();
  }
}
