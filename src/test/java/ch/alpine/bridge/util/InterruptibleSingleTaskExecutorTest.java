package ch.alpine.bridge.util;

import org.junit.jupiter.api.Test;

class InterruptibleSingleTaskExecutorTest {
  @Test
  void test() {
    InterruptibleSingleTaskExecutor iste = new InterruptibleSingleTaskExecutor();
    iste.submit(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
      }
    });
    iste.shutdown();
  }
}
