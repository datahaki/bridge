// code by jph
package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.io.ProcessLeaseGuard.Lease;

class ProcessLeaseGuardTest {
  @Test
  void test() {
    ProcessLeaseGuard plg1 = new ProcessLeaseGuard(getClass());
    ProcessLeaseGuard plg2 = new ProcessLeaseGuard(getClass());
    {
      Optional<Lease> optional = plg1.tryAcquire();
      assertTrue(optional.isPresent());
      assertFalse(plg2.tryAcquire().isPresent());
      if (optional.isPresent()) {
        Lease lease = optional.orElseThrow();
        assertTrue(lease.isOpen());
        lease.close();
      }
    }
    {
      Optional<Lease> optional = plg2.tryAcquire();
      assertTrue(optional.isPresent());
      if (optional.isPresent()) {
        Lease lease = optional.orElseThrow();
        assertTrue(lease.isOpen());
        lease.close();
      }
    }
  }
}
