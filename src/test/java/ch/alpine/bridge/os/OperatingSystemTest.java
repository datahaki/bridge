// code by jph
package ch.alpine.bridge.os;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.io.GitHubCI;

class OperatingSystemTest {
  @Test
  void test() {
    OperatingSystem operatingSystem = OperatingSystem.get();
    GitHubCI.println(operatingSystem);
    operatingSystem.lookAndFeels();
  }
}
