// code by jph
package ch.alpine.java.ref;

import java.util.Objects;
import java.util.Optional;

import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public enum TensorReflection {
  ;
  /** @param fieldSubdivide
   * @return Optional.empty() if given fieldSubdivide is null,
   * or fields specified by given fieldSubdivide are invalid */
  public static Optional<Tensor> of(FieldSubdivide fieldSubdivide) {
    if (Objects.nonNull(fieldSubdivide))
      try {
        return Optional.of(strict(fieldSubdivide));
      } catch (Exception exception) {
        // ---
      }
    return Optional.empty();
  }

  /** @param fieldSubdivide non-null
   * @return
   * @throws Exception if parsing of strings to tensors fails */
  public static Tensor strict(FieldSubdivide fieldSubdivide) {
    return Subdivide.of( //
        Tensors.fromString(fieldSubdivide.start()), //
        Tensors.fromString(fieldSubdivide.end()), //
        fieldSubdivide.intervals());
  }

  /** @param fieldSubdivide
   * @return
   * @throws Exception if parsing of strings to scalars fails */
  public static Clip clip(FieldSubdivide fieldSubdivide) {
    return Clips.interval( //
        Scalars.fromString(fieldSubdivide.start()), //
        Scalars.fromString(fieldSubdivide.end()));
  }
}