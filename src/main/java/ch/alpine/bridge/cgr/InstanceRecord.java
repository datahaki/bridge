// code by jph
package ch.alpine.bridge.cgr;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Supplier;

import ch.alpine.bridge.lang.FriendlyFormat;

/** @param <T>
 * @param subcls class, or enum that implements Class<T>
 * @param field name of static final field, or enum field name, otherwise
 * null if constructor is used
 * @param supplier */
public record InstanceRecord<T>(Class<?> subcls, Field field, Supplier<T> supplier) {
  public String packageName() {
    return subcls.getPackageName();
  }

  /** @return string expression that is suitable for display in human-readable gui elements,
   * for example: "Lagrange Interpolation Demo" */
  public String friendly() {
    return FriendlyFormat.defaultTitle(subcls) + suffix();
  }

  /** suitable for junit tests, for example: "ch.alpine.ascona.crv.LagrangeInterpolationDemo" */
  @Override
  public String toString() {
    return subcls.getName() + suffix();
  }

  private String suffix() {
    return Objects.isNull(field) ? "" : " " + field.getName();
  }
}
