// code by jph
package ch.alpine.java.wdog;

import java.util.Optional;

import ch.alpine.tensor.Scalar;

public class FrailValue<T> {
  private final Watchdog watchdog;
  private T value;

  public FrailValue(Scalar timeout) {
    watchdog = SoftWatchdog.barking(timeout);
  }

  /** @return */
  public Optional<T> getValue() {
    return watchdog.isBarking() //
        ? Optional.empty()
        : Optional.ofNullable(value);
  }

  public void setValue(T value) {
    this.value = value;
    watchdog.notifyWatchdog();
  }
}
