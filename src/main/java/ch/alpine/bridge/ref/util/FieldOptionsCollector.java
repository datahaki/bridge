// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldClips;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.c.UniformDistribution;

/* package */ class FieldOptionsCollector extends ObjectFieldIo {
  private final Map<String, List<String>> map = new LinkedHashMap<>();
  private final Map<String, Distribution> distributions = new LinkedHashMap<>();

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
          if (fieldClips.isFinite())
            distributions.put(key, UniformDistribution.of(fieldClips.clip()));
        }
      }
    }
  }

  /** @return mapping from field name to list of suggested value strings */
  public Map<String, List<String>> map() {
    return Collections.unmodifiableMap(map);
  }

  public Map<String, Distribution> distributions() {
    return Collections.unmodifiableMap(distributions);
  }
}
