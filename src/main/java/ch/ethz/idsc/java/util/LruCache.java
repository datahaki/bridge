// adapted by jph
package ch.ethz.idsc.java.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> extends LinkedHashMap<K, V> {
  private static final long serialVersionUID = 1720452365894008977L;
  // ---
  private final int maxSize;

  public LruCache(int maxSize) {
    super(maxSize * 4 / 3, 0.75f, true);
    this.maxSize = maxSize;
  }

  @Override
  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    return maxSize < size();
  }
}
