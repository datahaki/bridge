// code by jph
package ch.alpine.bridge.pro;

import java.util.List;

import ch.alpine.bridge.cgr.InstanceDiscovery;
import ch.alpine.bridge.cgr.InstanceRecord;

public enum RunProviderNamings {
  ;
  public static boolean of(String basePackage) {
    List<InstanceRecord<RunProvider>> list = //
        InstanceDiscovery.of(basePackage, RunProvider.class);
    boolean allValid = true;
    for (InstanceRecord<RunProvider> instanceRecord : list) {
      Class<?> subcls = instanceRecord.subcls();
      String name = subcls.getName();
      boolean status = false;
      status |= name.endsWith("Demo");
      status |= name.endsWith("Show");
      if (!status)
        System.err.println(name);
      allValid &= status;
    }
    return allValid;
  }
}
