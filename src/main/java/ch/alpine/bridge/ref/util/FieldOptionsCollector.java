// code by jph
package ch.alpine.bridge.ref.util;

import java.awt.Color;
import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldClips;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.MixtureDistribution;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.DiracDeltaDistribution;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.sca.Clip;

/* package */ class FieldOptionsCollector extends ObjectFieldAll {
  private final Map<String, List<String>> map = new LinkedHashMap<>();
  private final Map<String, Function<Random, String>> distributions = new LinkedHashMap<>();

  @Override // from ObjectFieldVisitor
  public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
    List<Object> list = fieldWrap.options(object);
    if (1 < list.size())
      map.put(key, list.stream().map(fieldWrap::toString).toList());
    else {
      Field field = fieldWrap.getField();
      Class<?> cls = field.getType();
      if (cls.equals(Scalar.class)) {
        FieldClip fieldClip = field.getAnnotation(FieldClip.class);
        if (Objects.nonNull(fieldClip)) {
          FieldClips fieldClips = FieldClips.wrap(fieldClip);
          Clip clip = fieldClips.clip();
          {
            map.put(key, Stream.of(clip.min(), clip.max()) //
                .filter(FiniteScalarQ::of) //
                .map(fieldWrap::toString) //
                .toList());
          }
          if (fieldClips.isFinite()) {
            Distribution distribution = MixtureDistribution.of(Tensors.vector(10, 1, 1), //
                UniformDistribution.of(clip), //
                DiracDeltaDistribution.of(clip.min()), //
                DiracDeltaDistribution.of(clip.max()));
            distributions.put(key, random -> fieldWrap.toString(RandomVariate.of(distribution, random)));
          }
        }
      }
      if (cls.equals(Color.class))
        distributions.put(key, random -> fieldWrap.toString(Randoms.color(random)));
      if (cls.equals(LocalTime.class))
        distributions.put(key, random -> fieldWrap.toString(Randoms.localTime(random)));
    }
  }

  /** @return mapping from field name to list of suggested value strings */
  public Map<String, List<String>> map() {
    return Collections.unmodifiableMap(map);
  }

  public Map<String, Function<Random, String>> distributions() {
    return Collections.unmodifiableMap(distributions);
  }
}
