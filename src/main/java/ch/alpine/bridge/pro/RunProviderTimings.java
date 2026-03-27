// code by jph
package ch.alpine.bridge.pro;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.alpine.bridge.cgr.InstanceDiscovery;
import ch.alpine.bridge.cgr.InstanceRecord;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.Timing;
import ch.alpine.tensor.sca.Round;

/** the motivation for {@link RunProviderTimings} is the following:
 * 
 * {@link InstanceDiscovery} involves invoking the default constructor.
 * some implementation of {@link RunProvider} or subclasses perform
 * computations in the default constructor which slows down {@link InstanceDiscovery}.
 * 
 * The {@link RunProviderTimings} prints a table sorted by duration
 * of instance retrieval. */
public enum RunProviderTimings {
  ;
  public static void of(String basePackage) throws Exception {
    List<InstanceRecord<RunProvider>> list = //
        InstanceDiscovery.of(basePackage, RunProvider.class);
    Map<InstanceRecord<RunProvider>, Scalar> map = new HashMap<>();
    for (InstanceRecord<RunProvider> instanceRecord : list) {
      Timing timing = Timing.started();
      instanceRecord.supplier().get();
      timing.stop();
      map.put(instanceRecord, timing.seconds());
    }
    Collections.sort(list, (i1, i2) -> Scalars.compare(map.get(i1), map.get(i2)));
    for (InstanceRecord<RunProvider> instanceRecord : list)
      IO.println(map.get(instanceRecord).maps(Round._3) + "\t" + instanceRecord);
  }
}
