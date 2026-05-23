// code by jph
package ch.alpine.bridge.ref;

import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
public abstract class Cacheable {
  @SuppressWarnings("unchecked")
  public final <T extends Cacheable> T copy() {
    return (T) Cacheables.copy(this);
  }

  @Override
  public final boolean equals(Object obj) {
    return Cacheables.deepEquals(this, obj);
  }

  @Override
  public final int hashCode() {
    return Cacheables.hash(this);
  }
}
