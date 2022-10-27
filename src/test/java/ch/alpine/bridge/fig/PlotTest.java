// code by jph
package ch.alpine.bridge.fig;

import java.io.IOException;
import java.io.Serializable;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.Serialization;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.tmp.ResamplingMethods;
import ch.alpine.tensor.tmp.TimeSeries;

class PlotTest {
  @Test
  void test() throws ClassNotFoundException, IOException {
    TimeSeries timeSeries = TimeSeries.empty(ResamplingMethods.LINEAR_INTERPOLATION);
    timeSeries.insert(DateTime.of(2022, 11, 3, 10, 45), Quantity.of(4, "kW"));
    timeSeries.insert(DateTime.of(2022, 11, 3, 20, 35), Quantity.of(2, "kW"));
    timeSeries.insert(DateTime.of(2022, 11, 4, 8, 15), Quantity.of(1, "kW"));
    Supplier<Clip> supplier = (Supplier<Clip> & Serializable) () -> timeSeries.isEmpty() //
        ? null
        : timeSeries.domain();
    Serialization.copy(timeSeries);
    Serialization.copy(supplier);
  }
}
