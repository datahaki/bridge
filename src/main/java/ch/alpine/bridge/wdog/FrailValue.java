// code by jph
package ch.alpine.bridge.wdog;

import java.util.Optional;

import ch.alpine.tensor.Scalar;

/** class hosts a mutable value that is provided
 * for a specified period of time, from the time
 * of invoking the set function. */
public class FrailValue<T> {
  private final Watchdog watchdog;
  /* non-final */
  private T value;

  /** @param timeout with unit compatible with "s", "ms", "us", "ns", ...
   * max age of value from the time
   * of invoking the set function */
  public FrailValue(Scalar timeout) {
    watchdog = SoftWatchdog.barking(timeout);
  }

  /** @return value as set by set function unless
   * more time than given timeout has elapsed */
  public Optional<T> getValue() {
    return watchdog.isBarking() //
        ? Optional.empty()
        : Optional.ofNullable(value);
  }

  /** @param value null is allowed */
  public void setValue(T value) {
    this.value = value;
    watchdog.notifyWatchdog();
  }
}
