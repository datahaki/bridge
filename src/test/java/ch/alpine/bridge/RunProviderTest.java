// code by jph
package ch.alpine.bridge;

import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import ch.alpine.bridge.cgr.InstanceDiscovery;
import ch.alpine.bridge.pro.RunProvider;
import ch.alpine.bridge.pro.SanityCheckRunProvider;

class RunProviderTest {
  @TestFactory
  Stream<DynamicTest> dynamicTests() throws Exception {
    return InstanceDiscovery.of(getClass().getPackageName(), RunProvider.class).stream() //
        .map(instanceRecord -> DynamicTest.dynamicTest(instanceRecord.toString(), //
            () -> new SanityCheckRunProvider().accept(instanceRecord)));
  }
}
