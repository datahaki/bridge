// code by jph
package sys.dat;

import java.util.Objects;

public class UniqueValue<T> {
  /** @param value non-null
   * @return */
  public static <T> UniqueValue<T> of(T value) {
    return new UniqueValue<>(Objects.requireNonNull(value));
  }

  public static <T> UniqueValue<T> ofNullable(T value) {
    return new UniqueValue<>(value);
  }

  public static <T> UniqueValue<T> empty() {
    return ofNullable(null);
  }

  // ---
  private T value;

  private UniqueValue(T value) {
    this.value = value;
  }

  /** @param candidate non-null
   * @throws Exception if value was set previously and is not equal to given candidate */
  public void set(T candidate) {
    Objects.requireNonNull(candidate);
    if (Objects.isNull(value))
      value = candidate;
    else //
    if (!value.equals(candidate))
      throw new IllegalArgumentException("attempt to change from " + value + " to " + candidate);
  }

  /** @return true if there is a value present, otherwise false. */
  public boolean isPresent() { // name from Optional
    return Objects.nonNull(value);
  }

  public T orElseThrow() { // name from Optional
    return Objects.requireNonNull(value);
  }

  /** @param fallback
   * @return the value if present, otherwise return value. */
  public T orElse(T fallback) { // name from Optional
    return isPresent() //
        ? value
        : fallback;
  }
}
