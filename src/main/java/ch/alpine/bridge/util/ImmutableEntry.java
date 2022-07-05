// code by jph
package ch.alpine.bridge.util;

import java.util.Map.Entry;

/* package */ enum ImmutableEntry {
  ;
  public static <K, V> Entry<K, V> of(K key, V value) {
    return new Entry<>() {
      @Override
      public K getKey() {
        return key;
      }

      @Override
      public V getValue() {
        return value;
      }

      @Override
      public V setValue(V value) {
        throw new UnsupportedOperationException();
      }
    };
  }
}
