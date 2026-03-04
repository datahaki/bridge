package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GraphemeCountTest {
  @Test
  void test() {
    String string = "Not yet implemented";
    int length = GraphemeCount.of(string);
    assertEquals(length, string.length());
  }
}
