// code by jph
package demo.tensor;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

/** Reference:
 * https://howtodoinjava.com/junit5/junit-5-test-lifecycle/ */
class Junit5TestLifeCycle {
  @BeforeAll
  static void beforeAll() {
    System.out.println("BEFORE ALL");
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("BEFORE EACH");
  }

  @Test
  void test1() {
    System.out.println(" TEST 1");
  }

  @Test
  void test2() {
    System.out.println(" TEST 2");
  }

  @RepeatedTest(3)
  void testRepead(RepetitionInfo repetitionInfo) {
    System.out.println(" REPEATED " + repetitionInfo.getCurrentRepetition());
  }

  @AfterEach
  void afterEach() {
    System.out.println("AFTER EACH");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("AFTER ALL");
  }
}
