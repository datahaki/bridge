// code by jph
package ch.alpine.bridge.pro;

import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import ch.alpine.bridge.cgr.InstanceDiscovery;

class RunProviderTest {
  public static String parentPackage(String packageName) {
    return packageName.substring(0, packageName.lastIndexOf('.'));
  }

  public String parentPackage() {
    return parentPackage(getClass().getPackageName());
  }

  @TestFactory
  Stream<DynamicTest> dynamicTests() throws Exception {
    return InstanceDiscovery.of(parentPackage(), RunProvider.class).stream() //
        .map(instanceRecord -> DynamicTest.dynamicTest(instanceRecord.toString(), //
            () -> new SanityCheckRunProvider().accept(instanceRecord)));
  }
}
