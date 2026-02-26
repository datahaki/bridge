// code by jph
package ch.alpine.bridge;

import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import ch.alpine.bridge.cgr.InstanceDiscovery;
import ch.alpine.bridge.pro.RunProvider;
import ch.alpine.bridge.pro.SanityCheckRunProvider;
import ch.alpine.bridge.pro.WindowProvider;

class RunProviderTest {
  @TestFactory
  Stream<DynamicTest> dynamicTests() {
    return InstanceDiscovery.of(getClass().getPackageName(), RunProvider.class).stream() //
        .map(instanceRecord -> DynamicTest.dynamicTest(instanceRecord.toString(), //
            () -> {
              RunProvider runProvider = instanceRecord.supplier().get();
              SanityCheckRunProvider.INSTANCE.accept(runProvider);
              if (runProvider instanceof WindowProvider) {
                // IO.println(runProvider.getClass());
              }
            }));
  }
}
