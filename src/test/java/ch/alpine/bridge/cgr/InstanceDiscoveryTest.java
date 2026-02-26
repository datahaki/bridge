// code by jph
package ch.alpine.bridge.cgr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.fft.DiscreteFourierTransform;
import ch.alpine.tensor.img.ImageResize;

class InstanceDiscoveryTest implements Consumer<DiscreteFourierTransform> {
  private static final AtomicInteger ai = new AtomicInteger();

  @Test
  void testWinProv() {
    List<InstanceRecord<ScalarUnaryOperator>> list = InstanceDiscovery.of("ch.alpine", ScalarUnaryOperator.class);
    assertTrue(150 <= list.size());
  }

  @Test
  void testDateTimeInterval() {
    List<InstanceRecord<ImageResize>> list = InstanceDiscovery.of("ch.alpine", ImageResize.class);
    assertEquals(list.size(), ImageResize.values().length);
  }

  @TestFactory
  Stream<DynamicTest> dynamicTests() {
    return InstanceDiscovery.of("ch.alpine", DiscreteFourierTransform.class).stream() //
        .map(instanceRecorder -> DynamicTest.dynamicTest(instanceRecorder.toString(), //
            () -> accept(instanceRecorder.supplier().get())));
  }

  @Override
  public void accept(DiscreteFourierTransform t) {
    // IO.println(t.toString());
    ai.getAndIncrement();
  }

  @AfterAll
  static void check() {
    assertTrue(12 <= ai.get());
  }
}
