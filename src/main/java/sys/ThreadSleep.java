// code by jph
package sys;

public enum ThreadSleep {
  ;
  private static final int factor = 1_000_000;

  // TODO use Thread.sleep Duration
  public static void nanos(long nanos) {
    if (0 < nanos) {
      long millis = nanos / factor;
      nanos -= millis * factor;
      try {
        Thread.sleep(millis, (int) nanos);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }
}
