package ch.alpine.bridge.io;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.io.ProcessLeaseGuard.Lease;

class ProcessLeaseGuardTest {
  @Test
  void test() {
    ProcessLeaseGuard processLeaseGuard = new ProcessLeaseGuard(getClass());
    Optional<Lease> optional = processLeaseGuard.tryAcquire();
    assertTrue(optional.isPresent());
    if (optional.isPresent()) {
      Lease lease = optional.orElseThrow();
      assertTrue(lease.isOpen());
      lease.close();
    }
  }
}
