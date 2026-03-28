// code by jph
package ch.alpine.bridge.fig;

import java.io.Serializable;
import java.util.Objects;

import ch.alpine.tensor.Scalar;

record AspectRatio(Type type, Scalar ratio) implements Serializable {
  enum Type {
    /** preserve characteristic of {@link Showable} or else maximizes fit */
    SELF,
    /** set by user as axis x / axis y scaling ratio */
    USER,
    /** ignores all preferences of {@link Showable} and instead maximizes drawing area */
    MFIT
  }

  public static AspectRatio self() {
    return new AspectRatio(Type.SELF, null);
  }

  public static AspectRatio user(Scalar ratio) {
    return new AspectRatio(Type.USER, Objects.requireNonNull(ratio));
  }

  public static AspectRatio mfit() {
    return new AspectRatio(Type.MFIT, null);
  }
}
