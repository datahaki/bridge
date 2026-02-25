// code by jph
package ch.alpine.bridge;

import java.util.function.Supplier;
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
        .map(Supplier::get) //
        .map(instance -> DynamicTest.dynamicTest(instance.toString(), //
            () -> {
              SanityCheckRunProvider.INSTANCE.accept(instance);
              if (instance instanceof WindowProvider) {
                IO.println(instance.getClass());
                Thread.sleep(2000);
              }
            }));
  }
}
