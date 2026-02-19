// code by jph
package ch.alpine.bridge.os;

import org.junit.jupiter.api.Test;

class OperatingSystemTest {
  @Test
  void test() {
    OperatingSystem operatingSystem = OperatingSystem.get();
    IO.println(operatingSystem);
    operatingSystem.lookAndFeels();
  }
}
